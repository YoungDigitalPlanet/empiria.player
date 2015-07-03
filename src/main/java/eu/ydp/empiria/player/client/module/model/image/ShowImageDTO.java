package eu.ydp.empiria.player.client.module.model.image;

import com.google.common.base.Objects;
import eu.ydp.gwtutil.client.util.geom.Size;

public class ShowImageDTO {
    public final String path;
    public final Size size;

    public ShowImageDTO(String path, Size size) {
        this.path = path;
        this.size = size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ShowImageDTO)) {
            return false;
        }
        ShowImageDTO other = (ShowImageDTO) obj;
        return Objects.equal(path, other.path) && Objects.equal(size, other.size);
    }
}
