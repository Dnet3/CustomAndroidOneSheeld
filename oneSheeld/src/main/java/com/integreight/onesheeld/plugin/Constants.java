/*
 * Copyright 2013 two forty four a.m. LLC <http://www.twofortyfouram.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.integreight.onesheeld.plugin;

import com.integreight.onesheeld.BuildConfig;

/**
 * Class of constants used by this Locale plug-in.
 */
public final class Constants {
    /**
     * Log tag for logcat messages.
     */
    public static final String LOG_TAG = "ToastSetting"; //$NON-NLS-1$

    /**
     * Flag to enable logcat messages.
     */
    public static final boolean IS_LOGGABLE = BuildConfig.DEBUG;

    /**
     * Private constructor prevents instantiation.
     *
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private Constants() {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }
}