/*
 * Copyright (C) 2011 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tomakehurst.wiremock.extension.responsetemplating.helpers;

import com.github.jknack.handlebars.Options;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class SystemEnvHelper extends HandlebarsHelper<String> {

    @Override
    public String apply(final String variableName, final Options options) throws IOException {
        if (StringUtils.isEmpty(variableName)) {
            return this.handleError("The variable name cannot be empty");
        }
        try {
            return getSystemEnvironment(variableName);

        } catch (AccessControlException e) {
            return this.handleError("Access to variable " + variableName + " is denied");
        }
    }

    private String getSystemEnvironment(final String variable) {
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            @Override
            public String run() {
                return System.getenv(variable);
            }
        });
    }
}
