package com.amazonaws.samples;

import java.io.File;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

//import software.amazon.awssdk.awscore.exception.AwsServiceException;
//import software.amazon.awssdk.core.exception.SdkClientException;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;


public class SendingImage {

//	public static void main(String[] args) {
//		uploadingImage("Image_0");
//	}

	public static void uploadingImage(String imageName) {
		// extracting image name from string imageName
		String[] val = imageName.split(" ");
		//System.out.println(val);
		String imageFileName = val[0];
		System.out.println(imageFileName);
		Regions clientRegion = Regions.US_EAST_2;
		String bucketName = "real1-time1-face11-data-collector2-bucket";
		// String stringObjKeyName = "awsimage.png";
		String fileObjKeyName = imageFileName + ".jpg";
		String fileName = "C:\\Users\\HelloAnshul\\Downloads\\project\\project\\"
				+ imageFileName + ".jpg";
		System.out.println(fileName);
//C:\Users\HelloAnshul\Downloads\project\project\Image_0.jpg
		try {

			File file = new File(fileName);
			int waitAttempts = 0;
			while (!file.exists() && waitAttempts < 10) {
				 try {
					Thread.sleep(500); // wait 0.5 sec
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					Thread.currentThread().interrupt(); // restore interrupted status
				}
				waitAttempts++;
			}
			if (!file.exists()) {
				System.out.println("File does not exist: " + fileName);
				return;
			}
			else{
				System.out.println("file exists at : " + fileName);
			}

			// This code expects that you have AWS credentials set up per:
			// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

			// Upload a text string as a new object.
			// s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

			//metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("image/jpeg");
			metadata.addUserMetadata("title", "someTitle");
			//

			// Upload a file as a new object with ContentType and title specified.
			PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
			request.setMetadata(metadata);
			s3Client.putObject(request);
		} catch (AmazonServiceException  e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}

	}
	// public static void main(String[] args) throws IOException { }
}

