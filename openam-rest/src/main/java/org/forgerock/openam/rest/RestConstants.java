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
 * Copyright 2015-2016 ForgeRock AS.
 */

package org.forgerock.openam.rest;

/**
 * Constants for use with creating CREST services.
 *
 * @since 13.0.0
 */
public final class RestConstants {

    /** The action ID for the template action. */
    public static final String TEMPLATE = "template";
    /** The action ID for the schema action. */
    public static final String SCHEMA = "schema";
    /** The action ID to return the type of an endpoint.  */
    public static final String GET_TYPE = "getType";
    /** The action ID to return all possible subschema types.  */
    public static final String GET_ALL_TYPES = "getAllTypes";
    /** The action ID to return all creatable subschema types. */
    public static final String GET_CREATABLE_TYPES = "getCreatableTypes";
    /** The action ID to return all created instances. */
    public static final String NEXT_DESCENDENTS = "nextdescendents";
    /** The URL parameter to filter out services that can't be created via the XUI */
    public static final String FOR_UI = "forUI";

    /** The name field constant. **/
    public final static String NAME = "name";
    /** The collection field constants. **/
    public final static String COLLECTION = "collection";
    /** The result field constant. **/
    public final static String RESULT = "result";

    private RestConstants() {
        // No extensions.
    }

}
