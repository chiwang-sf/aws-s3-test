package aws;

import static aws.Constants.RootPath;

public class Experiments {

    public void executionWithTimeMeasure(Runnable func, String experimentName){

        System.out.println(String.format("Start to run exp: %s.", experimentName));

        long startTime = System.currentTimeMillis();

        func.run();

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println(String.format("Execution time for exp %s is %d milliseconds.", experimentName, timeElapsed));
    }

    public void Upload_SingleBigZip_VS_SingleBigZipWithMultiPart() {
        System.out.println("Upload_SingleBigZip_VS_SingleBigZipWithMultiPart Started.");

        executionWithTimeMeasure(() -> {
            Basic.UploadSingleFile("/Users/chi.wang/tmp/alpine.zip",RootPath +"BasicOperation/SingleFile/simpleupload-alpine10MB.zip");
        }, "Simple upload single big zip file (10MB)");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .UploadSingleFile("/Users/chi.wang/tmp/alpine.zip",RootPath +"BasicOperation/SingleFile/multiPart-upload-alpine10MB.zip");
        }, "MultiPart upload single big zip file (10MB)");

        System.out.println("Upload_SingleBigZip_VS_SingleBigZipWithMultiPart Finished.");
    }

    public void Upload_SingleBigZip_VS_SingleBigZipWithMultiPart_2() {
        System.out.println("Upload_SingleBigZip_VS_SingleBigZipWithMultiPart 2 Started.");

        executionWithTimeMeasure(() -> {
            Basic.UploadSingleFile("/Users/chi.wang/tmp/image_dataset2/4.zip",RootPath +"BasicOperation/SingleFile/simpleupload-image-300MB.zip");
        }, "Simple upload single big zip file (300MB)");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .UploadSingleFile("/Users/chi.wang/tmp/image_dataset2/4.zip",RootPath +"BasicOperation/SingleFile/multiPart-upload-image-300MB.zip");
        }, "MultiPart upload single big zip file (300MB)");

        System.out.println("Upload_SingleBigZip_VS_SingleBigZipWithMultiPart Finished.");
    }

    public void Upload_SingleBigZip_VS_SingleBigZipWithMultiPart_3() {
        System.out.println("Upload_SingleBigZip_VS_SingleBigZipWithMultiPart 3 Started.");

        executionWithTimeMeasure(() -> {
            Basic.UploadSingleFile("/Users/chi.wang/tmp/abi_evs_final_results1-2G.zip",RootPath +"BasicOperation/SingleFile/simpleupload-abi_evs_final_results-2G.zip");
        }, "Simple upload single big zip file (1.9GB)");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .UploadSingleFile("/Users/chi.wang/tmp/abi_evs_final_results1-2G.zip",RootPath +"BasicOperation/SingleFile/multiPart-upload-abi_evs_final_results-2G.zip");
        }, "MultiPart upload single big zip file (1.9GB)");

        System.out.println("Upload_SingleBigZip_VS_SingleBigZipWithMultiPart Finished.");
    }

    public void Upload_Directories_VS_SingleZip() {
        System.out.println("Upload_Directories_VS_SingleZip Started.");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples.UploadDirectory("/Users/chi.wang/tmp/image_dataset/4",RootPath +"BasicOperation/Directory/4-300MB");
        }, "Upload_Directories (300MB)");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .UploadSingleFile("/Users/chi.wang/tmp/image_dataset2/4.zip",RootPath +"BasicOperation/SingleFile/multiPart-upload-image-300MB-4.zip");
        }, "MultiPart upload single zip file (300MB)");


        System.out.println("Upload_Directories_VS_SingleZip Finished");
    }

    public void Upload_Directories_VS_UploadListZipFiles_VS_Big_ZipFile() {
        System.out.println("Upload_Directories_VS_UploadListZipFiles_VS_Big_ZipFile Started.");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples.UploadDirectory("/Users/chi.wang/tmp/image_dataset",RootPath +"BasicOperation/Directory/FullDir-1.9GB");
        }, "Upload_Directories (1.9GB)");


        // Upload by small groups of zip doesn't work, ts will stuck at uploading.

//        executionWithTimeMeasure(() -> {
//            TransferManagerSamples
//                    .UploadDirectory("/Users/chi.wang/tmp/image_dataset2",RootPath +"BasicOperation/Directory/ListOfFiles-DirectoryUpload-1.9GB");
//        }, "Upload list of files (10 files, total 1.9GB)");
////
//        executionWithTimeMeasure(() -> {
//            TransferManagerSamples
//                    .UploadListOfFiles("/Users/chi.wang/tmp/image_dataset2",RootPath +"BasicOperation/Directory/ListOfFiles-ListAPIUpload-1.9GB");
//        }, "Upload list of files (10 files, total 1.9GB)");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .UploadSingleFile("/Users/chi.wang/tmp/abi_evs_final_results1-2G.zip",RootPath +"BasicOperation/SingleFile/multiPart-upload-abi_evs-1.9GB.zip");
        }, "MultiPart upload single zip file (1.9GB)");

        System.out.println("Upload_Directories_VS_UploadListZipFiles_VS_Big_ZipFile Finished");

    }

    // 300 MB images vs one zip
    public void Download_Directories_VS_SimpleDownload_SingleZip_VS_MultiPartDownload_SingleZip() {
        System.out.println("Download_Directories_VS_SingleZip Started.");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .DownloadDirectory("private/chi.wang/BasicOperation/Directory/4-300MB/","/Users/chi.wang/tmp/aws/test_download_dir");
        }, "Download directory from S3 300MB");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .DownloadMultiPartSingleFile("private/chi.wang/BasicOperation/SingleFile/multiPart-upload-image-300MB-4.zip","/Users/chi.wang/tmp/aws/test_download_single/300-multipart.zip");
        }, "Multipart Download single zip from S3 300MB");

        executionWithTimeMeasure(() -> {
            Basic.DownloadSingleFile(
                    "private/chi.wang/BasicOperation/SingleFile/multiPart-upload-image-300MB-4.zip","/Users/chi.wang/tmp/aws/test_download_single/300-single.zip");
        }, "Simple download single zip from S3 300MB");

        System.out.println("Download_Directories_VS_SingleZip Finished.");
    }

    public void Download_Directories_VS_FetchListObjects_VS_Download_SingleZip_2GB() {
        System.out.println("Download_Directories_VS_FetchListObjects_VS_Download_SingleZip_2GB Started.");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .DownloadDirectory("private/chi.wang/BasicOperation/Directory/FullDir-1.9GB/","/Users/chi.wang/tmp/aws/test_download_dir");
        }, "Download directory from S3 1.9GB");

        executionWithTimeMeasure(() -> {
            TransferManagerSamples
                    .DownloadMultiPartSingleFile("private/chi.wang/BasicOperation/SingleFile/multiPart-upload-abi_evs-1.9GB.zip","/Users/chi.wang/tmp/aws/test_download_single/300-multipart.zip");
        }, "Multipart Download single zip from S3 1.9GB");

        executionWithTimeMeasure(() -> {
            Basic.DownloadSingleFile(
                    "private/chi.wang/BasicOperation/SingleFile/multiPart-upload-abi_evs-1.9GB.zip","/Users/chi.wang/tmp/aws/test_download_single/300-single.zip");
        }, "Simple download single zip from S3 1.9GB");

        System.out.println("Download_Directories_VS_FetchListObjects_VS_Download_SingleZip_2GB Finished.");
    }
}
