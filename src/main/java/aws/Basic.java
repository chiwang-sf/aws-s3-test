package aws;

import java.io.*;
import java.net.URL;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

public class Basic {

    public static void UploadSingleFile(String file, String s3Name) {

        File f = new File(file);
        if (!f.exists()) {
            System.out.println(String.format("Couldn't find file %s.", file));
            return;
        }

        String bucketName = Constants.RootBucket;

        try {

            AmazonS3 s3 = new AmazonS3Client();
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, s3Name, f);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/zip");
            request.setMetadata(metadata);
            s3.putObject(request);

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (
                AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    public static void DownloadSingleFile(String objectKey, String destFilePath) {
        try {
            AmazonS3 s3 = new AmazonS3Client();
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

            GetObjectRequest s3ObjectReq = new GetObjectRequest(Constants.RootBucket, objectKey);
            S3Object downlodedObjectMD = s3.getObject(s3ObjectReq);
            InputStream in = downlodedObjectMD.getObjectContent();

            File targetFile = new File(destFilePath);
            OutputStream outStream = new FileOutputStream(targetFile);

            byte[] buffer = new byte[8 * 1024 * 1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            in.close();
            outStream.close();
        } catch (
                AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    // Doc & Code: https://docs.aws.amazon.com/AmazonS3/latest/dev/ShareObjectPreSignedURLJavaSDK.html
    public static void GeneratePresignUrl(String objectKey, int expireDuration) {
        try {
            AmazonS3 s3 = new AmazonS3Client();
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(Constants.RootBucket, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed URL: " + url.toString());
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }
    }

    public static void ListObjects(String prefix) {
        try {
            AmazonS3 s3 = new AmazonS3Client();
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

            System.out.println("Listing objects");

            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                    .withBucketName(Constants.RootBucket)
                    .withDelimiter("/")
                    .withPrefix(prefix));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();
        } catch (
                AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
