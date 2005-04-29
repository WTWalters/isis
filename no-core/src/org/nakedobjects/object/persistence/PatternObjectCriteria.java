package org.nakedobjects.object.persistence;

import org.nakedobjects.object.NakedObject;
import org.nakedobjects.object.NakedObjectSpecification;
import org.nakedobjects.object.reflect.NakedObjectField;


public class PatternObjectCriteria implements InstancesCriteria {
    private final boolean includeSubclasses;
    private final NakedObject pattern;

    public PatternObjectCriteria(NakedObject pattern, boolean includeSubclasses) {
        this.pattern = pattern;
        this.includeSubclasses = includeSubclasses;
    }

    public boolean matches(NakedObject object) {
        NakedObjectSpecification requiredType = pattern.getSpecification();
        return requiredType.equals(object.getSpecification()) && matchesPattern(pattern, object);
    }

    private boolean matchesPattern(NakedObject pattern, NakedObject instance) {
        NakedObject object = instance;
        NakedObjectSpecification nc = object.getSpecification();
        NakedObjectField[] fields = nc.getFields();

        for (int f = 0; f < fields.length; f++) {
            NakedObjectField fld = fields[f];

            // are ignoring internal collections - these probably should be
            // considered
            // ignore derived fields - there is no way to set up these fields
            if (fld.isDerived()) {
                continue;
            }

            if (fld.isValue()) {
                // find the objects
                NakedObject reqd = (NakedObject) pattern.getField(fld);
                NakedObject search = (NakedObject) object.getField(fld);

                // if pattern contains empty value then it matches anything
                if (reqd.isEmpty(fld)) {
                    continue;
                }

                // compare the titles
                String r = reqd.titleString().toString().toLowerCase();
                String s = search.titleString().toString().toLowerCase();

                // if the pattern occurs in the object
                if (s.indexOf(r) == -1) {
                    return false;
                }
            } else {
                // find the objects
                NakedObject reqd = (NakedObject) pattern.getField(fld);
                NakedObject search = (NakedObject) object.getField(fld);

                // if pattern contains null reference then it matches anything
                if (reqd == null) {
                    continue;
                }

                // otherwise there must be a reference, else they can never
                // match
                if (search == null) {
                    return false;
                }

                if (reqd != search) {
                    return false;
                }
            }
        }

        return true;
    }

    public NakedObjectSpecification getSpecification() {
        return pattern.getSpecification();
    }

    public boolean includeSubclasses() {
        return includeSubclasses;
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business
 * objects directly to the user. Copyright (C) 2000 - 2005 Naked Objects Group
 * Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address
 * of Naked Objects Group is Kingsway House, 123 Goldworth Road, Woking GU21
 * 1NR, UK).
 */