package bestgymever.controller;

import bestgymever.models.*;

@FunctionalInterface
public interface FunInt {

    public SuperModel update(SuperModel m);

    default FunInt andThen(FunInt after) {
        return m -> after.update(update(m));
    }
}
