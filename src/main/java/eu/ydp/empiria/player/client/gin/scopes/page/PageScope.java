package eu.ydp.empiria.player.client.gin.scopes.page;

import eu.ydp.gwtutil.client.gin.scopes.AbstractCustomScope;

public class PageScope extends AbstractCustomScope {

    private Integer pageNumber;

    public PageScope(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PageScope other = (PageScope) obj;
        if (pageNumber == null) {
            if (other.pageNumber != null) {
                return false;
            }
        } else if (!pageNumber.equals(other.pageNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return pageNumber;
    }
}
