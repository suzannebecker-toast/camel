/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.huaweicloud.frs;

import com.huaweicloud.sdk.frs.v2.model.CompareFaceByUrlResponse;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.huaweicloud.frs.constants.FaceRecognitionProperties;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaceVerificationWithImageUrlAndMockClientTest extends CamelTestSupport {
    TestConfiguration testConfiguration = new TestConfiguration();

    @BindToRegistry("frsClient")
    FrsClientMock frsClient = new FrsClientMock(null);

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:trigger_route")
                        .setProperty(FaceRecognitionProperties.FACE_IMAGE_URL,
                                constant(testConfiguration.getProperty("imageUrl")))
                        .setProperty(FaceRecognitionProperties.ANOTHER_FACE_IMAGE_URL,
                                constant(testConfiguration.getProperty("anotherImageUrl")))
                        .to("hwcloud-frs:faceVerification?"
                            + "accessKey=" + testConfiguration.getProperty("accessKey")
                            + "&secretKey=" + testConfiguration.getProperty("secretKey")
                            + "&projectId=" + testConfiguration.getProperty("projectId")
                            + "&region=" + testConfiguration.getProperty("region")
                            + "&ignoreSslVerification=true"
                            + "&frsClient=#frsClient")
                        .log("perform faceVerification successfully")
                        .to("mock:perform_face_verification_result");
            }
        };
    }

    @Test
    public void testFaceVerification() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:perform_face_verification_result");
        mock.expectedMinimumMessageCount(1);
        template.sendBody("direct:trigger_route", "");
        Exchange responseExchange = mock.getExchanges().get(0);
        mock.assertIsSatisfied();

        assertTrue(responseExchange.getIn().getBody() instanceof CompareFaceByUrlResponse);
        CompareFaceByUrlResponse response = (CompareFaceByUrlResponse) responseExchange.getIn().getBody();
        assertEquals(response.getImage1Face(), MockResult.getCompareFaceResult());
        assertEquals(response.getImage2Face(), MockResult.getCompareFaceResult());
        assertEquals(response.getSimilarity(), 1.0);
    }

}
