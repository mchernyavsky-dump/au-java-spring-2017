package ru.spbau.mit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Function2<A, B, C> {
    @Nullable
    abstract C apply(@Nullable final A arg1, @Nullable final B arg2);

    @NotNull
    public <D> Function2<? super A, ? super B, ? extends D>
    compose(@NotNull final Function1<? super C, ? extends D> other) {
        return new Function2<>() {
            @Nullable
            @Override
            D apply(@Nullable final A arg1, @Nullable final B arg2) {
                return other.apply(Function2.this.apply(arg1, arg2));
            }
        };
    }

    @NotNull
    public Function1<? super B, ? extends C> bind1(@Nullable final A arg1) {
        return new Function1<>() {
            @Nullable
            @Override
            C apply(@Nullable final B arg2) {
                return Function2.this.apply(arg1, arg2);
            }
        };
    }

    @NotNull
    public Function1<? super A, ? extends C> bind2(@Nullable final B arg2) {
        return new Function1<>() {
            @Nullable
            @Override
            C apply(@Nullable final A arg1) {
                return Function2.this.apply(arg1, arg2);
            }
        };
    }

    @NotNull
    public Function1<? super A, Function1<? super B, ? extends C>> curry() {
        return new Function1<>() {
            @NotNull
            @Override
            Function1<? super B, ? extends C> apply(@Nullable final A arg1) {
                return Function2.this.bind1(arg1);
            }
        };
    }
}
