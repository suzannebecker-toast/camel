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

import com.huaweicloud.sdk.frs.v2.model.DetectLiveByFileResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.huaweicloud.frs.constants.FaceRecognitionProperties;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LiveDetectionWithVideoFileTest extends CamelTestSupport {
    TestConfiguration testConfiguration = new TestConfiguration();

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:trigger_route")
                        .setProperty(FaceRecognitionProperties.FACE_VIDEO_FILE_PATH,
                                constant(testConfiguration.getProperty("videoFilePath")))
                        .to("hwcloud-frs:faceLiveDetection?"
                            + "accessKey=" + testConfiguration.getProperty("accessKey")
                            + "&secretKey=" + testConfiguration.getProperty("secretKey")
                            + "&projectId=" + testConfiguration.getProperty("projectId")
                            + "&region=" + testConfiguration.getProperty("region")
                            + "&ignoreSslVerification=true")
                        .log("perform faceLiveDetection successful")
                        .to("mock:perform_live_detection_result");
            }
        };
    }

    /**
     * Following test cases should be manually enabled to perform test against the actual Huawei Cloud Face Recognition
     * service with real user credentials. To perform this test, manually comment out the @Disabled annotation and enter
     * relevant service parameters in the placeholders above (static variables of this test class)
     *
     * @throws Exception Exception
     */
    @Test
    @Disabled("Manually comment out this line once you configure service parameters in placeholders above")
    public void testCelebrityRecognition() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:perform_live_detection_result");
        mock.expectedMinimumMessageCount(1);
        template.sendBody("direct:trigger_route", "");
        Exchange responseExchange = mock.getExchanges().get(0);

        mock.assertIsSatisfied();

        assertTrue(responseExchange.getIn().getBody() instanceof DetectLiveByFileResponse);
    }

}
