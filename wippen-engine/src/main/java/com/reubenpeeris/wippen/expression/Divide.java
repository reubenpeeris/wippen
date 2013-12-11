package com.reubenpeeris.wippen.expression;


public final class Divide extends PairNode {
    private static final Validator DIVIDE_VALIDATOR =  new Validator() {
        @Override
        public boolean isValid(int left, int right) {
            return left % right == 0;
        }

        @Override
        public boolean canHaveLeftChildOfTypeEqual() {
            return false;
        }
    };

    public static NodeBuilder builder() {
        return new NodeBuilder(DIVIDE_VALIDATOR) {
            @Override
            protected final PairNode build(Expression left, Expression right) {
                return new Divide(left, right);
            }
        };
    }

    public Divide(Expression left, Expression right) {
        super(left, right, DIVIDE_VALIDATOR);
    }

    @Override
    protected int getValue(int left, int right) {
        return left / right;
    }

    @Override
    protected String getOperatorSymbol() {
        return "/";
    }
}
