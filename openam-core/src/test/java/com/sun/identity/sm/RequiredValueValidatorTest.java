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
package com.sun.identity.sm;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import org.testng.annotations.Test;

public class RequiredValueValidatorTest {

    private final RequiredValueValidator validator = new RequiredValueValidator();

    @Test
    public void checkReturnFalseIfEmptySet() {
        //given

        //when
        boolean result = validator.validate(Collections.<String>emptySet());

        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void checkReturnFalseIfEmptyStringInSet() {
        //given

        //when
        boolean result = validator.validate(Collections.singleton(""));

        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void checkReturnTrueIfBlankStringInSet() {
        //given

        //when
        boolean result = validator.validate(Collections.singleton(" "));

        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void checkReturnTrueIfSetValid() {
        //given
        HashSet<String> set = new HashSet<>();
        set.add("one");
        set.add("two");
        set.add("three");
        set.add("four");

        //when
        boolean result = validator.validate(set);

        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void checkReturnFalseIfSetInvalid() {
        //given
        HashSet<String> set = new HashSet<>();
        set.add("one");
        set.add("two");
        set.add("three");
        set.add("");

        //when
        boolean result = validator.validate(set);

        //then
        assertThat(result).isEqualTo(false);
    }
}
