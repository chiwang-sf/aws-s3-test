package cw.sf.einstein;

import aws.Basic;
import aws.Experiments;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Experiments exp = new Experiments();
//        exp.Upload_SingleBigZip_VS_SingleBigZipWithMultiPart();
//        exp.Upload_SingleBigZip_VS_SingleBigZipWithMultiPart_2();
//        exp.Upload_SingleBigZip_VS_SingleBigZipWithMultiPart_3();

//        Basic.GeneratePresignUrl("private/chi.wang/datasets/{orgid}-{dsid}/examples/changeset1/beach-987cc8eb6264ac7b2b1b250fe2dafbad", 5);
//        Basic.GeneratePresignUrl("private/chi.wang/BasicOperation/SingleFile/multiPart-upload-image-300MB-4.zip", 5);
        // exp.Upload_Directories_VS_SingleZip();
        // exp.Upload_Directories_VS_UploadListZipFiles_VS_Big_ZipFile();
        // exp.Download_Directories_VS_SimpleDownload_SingleZip_VS_MultiPartDownload_SingleZip();

        //exp.Upload_Directories_VS_SingleZip();
        //exp.Upload_Directories_VS_UploadListZipFiles_VS_Big_ZipFile();

        exp.Download_Directories_VS_SimpleDownload_SingleZip_VS_MultiPartDownload_SingleZip();
        exp.Download_Directories_VS_FetchListObjects_VS_Download_SingleZip_2GB();
    }
}
