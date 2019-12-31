/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.events.model;

import guru.bug.austras.apt.core.common.model.QualifierSetModel;

import java.util.List;

public class EventsBroadcasterModel {
    private String eventsInterface;
    private QualifierSetModel qualifier;
    private String packageName;
    private String simpleName;
    private List<MethodModel> methods;

    public String getEventsInterface() {
        return eventsInterface;
    }

    public void setEventsInterface(String eventsInterface) {
        this.eventsInterface = eventsInterface;
    }

    public QualifierSetModel getQualifier() {
        return qualifier;
    }

    public void setQualifier(QualifierSetModel qualifier) {
        this.qualifier = qualifier;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public List<MethodModel> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodModel> methods) {
        this.methods = methods;
    }
}
