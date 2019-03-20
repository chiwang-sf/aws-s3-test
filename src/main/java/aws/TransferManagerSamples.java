package aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.*;

import java.io.File;
import java.util.ArrayList;

// Doc: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-transfermanager.html#transfermanager-upload-file
// Complete AWS examples: https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/XferMgrUpload.java
public class TransferManagerSamples {

    public static void UploadDirectory(String directoryPath, String s3Name) {
        try {
            MultipleFileUpload xfer = TransferManagerSingleton.getInstance().uploadDirectory(Constants.RootBucket,
                    s3Name, new File(directoryPath), true);
            XferMgrProgress.showTransferProgress(xfer);
            XferMgrProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public static void UploadSingleFile(String filePath, String s3Name){
        File f = new File(filePath);
        try {

            Upload xfer = TransferManagerSingleton.getInstance().upload(Constants.RootBucket, s3Name, f);
            XferMgrProgress.showTransferProgress(xfer);
            XferMgrProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public static void UploadListOfFiles(String dir, String s3Name) {

        File folder = new File(dir);
        ArrayList<File> files = new ArrayList<File>();
        for (File file : folder.listFiles()) {
            files.add(file);
        }

        try {
            MultipleFileUpload xfer = TransferManagerSingleton.getInstance().uploadFileList(Constants.RootBucket,
                    s3Name, new File(dir), files);
            XferMgrProgress.showTransferProgress(xfer);
            XferMgrProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public static void DownloadDirectory(String s3Key, String destDir) {
        try {

            AmazonS3 s3 = new AmazonS3Client();
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

            TransferManager tm = new TransferManager(s3);
            MultipleFileDownload xfer = tm.downloadDirectory(
                    Constants.RootBucket, s3Key, new File(destDir));

//            MultipleFileDownload xfer = TransferManagerSingleton.getInstance().downloadDirectory(
//                    Constants.RootBucket, s3Key, new File(destDir));
            XferMgrProgress.showTransferProgress(xfer);
            XferMgrProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public static void DownloadMultiPartSingleFile(String s3Key, String destFilePath){
        File f = new File(destFilePath);
        try {
            Download xfer = TransferManagerSingleton.getInstance().download(Constants.RootBucket, s3Key, f);
            XferMgrProgress.showTransferProgress(xfer);
            XferMgrProgress.waitForCompletion(xfer);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}

class TransferManagerSingleton
{
    // static variable single_instance of type Singleton
    private static TransferManager single_instance = null;

    // private constructor restricted to this class itself
    private TransferManagerSingleton()
    { }

    // static method to create instance of Singleton class
    public static TransferManager getInstance()
    {
        if (single_instance == null) {
            AmazonS3 s3 = new AmazonS3Client();
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

            single_instance = new TransferManager(s3);
        }

        return single_instance;
    }
}
