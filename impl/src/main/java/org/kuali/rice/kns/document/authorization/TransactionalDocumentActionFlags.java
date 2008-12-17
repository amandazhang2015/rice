/*
 * Copyright 2007 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kns.document.authorization;

/**
 * TransactionalDocument-specific flags used for authorization checks.
 */
@Deprecated
final public class TransactionalDocumentActionFlags extends DocumentActionFlags {

    /**
     * Default constructor.
     */
    public TransactionalDocumentActionFlags() {
    }

    /**
     * Handles constructing a TransactionalDocumentActionFlags object instance from another DocumentActionFlags instance.
     * 
     * @param flags
     */
    public TransactionalDocumentActionFlags(DocumentActionFlags flags) {
        super(flags);

        if (flags instanceof TransactionalDocumentActionFlags) {
            TransactionalDocumentActionFlags tflags = (TransactionalDocumentActionFlags) flags;
        }
    }

    /**
     * Debugging method, simplifies comparing another instance of this class to this one
     * 
     * @param other
     * @return String
     */
    public String diff(DocumentActionFlags other) {
        StringBuffer s = new StringBuffer(super.diff(other));

        if (other instanceof TransactionalDocumentActionFlags) {
            TransactionalDocumentActionFlags tother = (TransactionalDocumentActionFlags) other;
        }

        return s.toString();
    }
}