package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractSection<T> implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.hashCode() == o.hashCode();
    }

    public boolean isEmpty() { return false; }
    public void doWriteData(DataOutputStream outputStream) throws IOException {}
}
