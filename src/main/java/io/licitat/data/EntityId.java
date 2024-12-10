package io.licitat.data;

import java.util.Objects;

public final class EntityId<T> {
    private final int id;

    /**
     * Private constructor to control instantiation
     */
    private EntityId(int id) {
        this.id = id;
    }

    /**
     * Static factory method to create a new TypedId
     */
    public static <T> EntityId<T> of(int id) {
        return new EntityId<>(id);
    }

    /**
     * Getter for the ID
     */
    public int value() {
        return id;
    }

    /**
     * Value semantics: Override equals and hashCode
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EntityId<?> typedId = (EntityId<?>) obj;
        return id == typedId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Human-readable representation
     */
    @Override
    public String toString() {
        return String.format("EntityId{id=%d}", id);
    }
}


