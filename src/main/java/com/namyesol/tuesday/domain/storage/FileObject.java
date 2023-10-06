package com.namyesol.tuesday.domain.storage;

public class FileObject {
    
    private String originalFileName;
    private String storeFileName;
    
    public FileObject() {}

    public FileObject(String originalFileName, String storeFileName) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((originalFileName == null) ? 0 : originalFileName.hashCode());
        result = prime * result + ((storeFileName == null) ? 0 : storeFileName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileObject other = (FileObject) obj;
        if (originalFileName == null) {
            if (other.originalFileName != null)
                return false;
        } else if (!originalFileName.equals(other.originalFileName))
            return false;
        if (storeFileName == null) {
            if (other.storeFileName != null)
                return false;
        } else if (!storeFileName.equals(other.storeFileName))
            return false;
        return true;
    }
    
    
}
