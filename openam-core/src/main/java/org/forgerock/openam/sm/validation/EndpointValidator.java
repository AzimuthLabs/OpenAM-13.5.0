/*
* The contents of this file are subject to the terms of the Common Development and
* Distribution License (the License). You may not use this file except in compliance with the
* License.
*
* You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
* specific language governing permission and limitations under the License.
*
* When distributing Covered Software, include this CDDL Header Notice in each file and include
* the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
* Header, with the fields enclosed by brackets [] replaced by your own identifying
* information: "Portions copyright [year] [name of copyright owner]".
*
* Copyright 2016 ForgeRock AS.
*/
package org.forgerock.openam.sm.validation;

import com.sun.identity.sm.ServiceAttributeValidator;
import java.util.Collections;
import java.util.Set;

/**
 * Checks that an attribute is either a URL or a Hostname.
 */
public class EndpointValidator implements ServiceAttributeValidator {

    private final HostnameValidator hostnameValidator;
    private final URLValidator urlValidator;

    /**
     * Constructs a new EndpointValidator, containing a new HostnameValidator and URLValidator.
     */
    public EndpointValidator() {
        this.hostnameValidator = new HostnameValidator();
        this.urlValidator = new URLValidator();
    }

    @Override
    public boolean validate(Set<String> values) {

        for (String value : values) {

            Set<String> underTest = Collections.singleton(value);

            if (!hostnameValidator.validate(underTest) && !urlValidator.validate(underTest)) {
                return false;
            }
        }
        return true;
    }
}
