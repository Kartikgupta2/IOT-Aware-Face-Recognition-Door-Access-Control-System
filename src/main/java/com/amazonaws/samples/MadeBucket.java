package com.amazonaws.samples;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

public class MadeBucket {

	public static void createBucket() {

		Regions clientRegion = Regions.US_EAST_2;
		String bucketName = "real1-time1-face11-data-collector2-bucket";

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider())
					.withRegion(clientRegion).build();

			if (!s3Client.doesBucketExistV2(bucketName)) {
				// Because the CreateBucketRequest object doesn't specify a region, the
				// bucket is created in the region specified in the client.
				s3Client.createBucket(new CreateBucketRequest(bucketName));

				// Verify that the bucket was created by retrieving it and checking its
				// location.
				String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
				System.out.println("Bucket created at location: " + bucketLocation);
			} else {
    				System.out.println("Bucket already exists: " + bucketName);
			}

		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it and returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
	}
}


//  if (!s3Client.doesBucketExistV2(bucketName)) {
//             int attempts = 0;
//             boolean created = false;
//             while (!created && attempts < 5) {  // Retry up to 5 times
//                 try {
//                     s3Client.createBucket(new CreateBucketRequest(bucketName));
//                     String bucketLocation = s3Client.getBucketLocation(
//                         new GetBucketLocationRequest(bucketName));
//                     System.out.println("Bucket created at location: " + bucketLocation);
//                     created = true;
//                 } catch (AmazonS3Exception e) {
//                     if ("OperationAborted".equals(e.getErrorCode())) {
//                         attempts++;
//                         System.out.println("Bucket creation in progress, retrying... attempt " + attempts);
//                         Thread.sleep(3000); // wait 3 seconds before retry
//                     } else {
//                         throw e;
//                     }
//                 }
//             }
//             if (!created) {
//                 System.out.println("Bucket could not be created after retries");
//             }
//         } else {
//             System.out.println("Bucket already exists: " + bucketName);
//         }

