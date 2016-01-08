/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.applib.layout.v1_0;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

@XmlType(
        propOrder = {
                "id"
                , "actions"
                , "layout"
        }
)
public class Property implements ActionHolder, Serializable {

    private static final long serialVersionUID = 1L;

    public Property() {
    }

    public Property(final String id) {
        this.id = id;
    }

    private String id;

    /**
     * Property identifier, being the getter method without "get" or "is" prefix, first letter lower cased.
     */
    @XmlAttribute(required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private List<Action> actions = Lists.newArrayList();

    /**
     * The <code>&lt;actions&gt;</code> element must be present but can be empty.
     */
    @XmlElementWrapper(required = true)
    @XmlElement(name = "action", required = false)
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    private PropertyLayout layout = new PropertyLayout();

    @XmlElement(required = true)
    public PropertyLayout getLayout() {
        return layout;
    }

    public void setLayout(PropertyLayout layout) {
        this.layout = layout;
    }




    private PropertyGroup owner;
    /**
     * Owner.
     *
     * <p>
     *     Set programmatically by framework after reading in from XML.
     * </p>
     */
    @XmlTransient
    public PropertyGroup getOwner() {
        return owner;
    }

    public void setOwner(final PropertyGroup owner) {
        this.owner = owner;
    }

}