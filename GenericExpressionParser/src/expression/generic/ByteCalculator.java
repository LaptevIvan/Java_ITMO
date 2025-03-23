package expression.generic;

public class ByteCalculator implements GenericCalculate<Byte> {
    @Override
    public Byte add(Byte val1, Byte val2) {
        return (byte) (val1 + val2);
    }

    @Override
    public Byte substract(Byte val1, Byte val2) {
        return (byte) (val1 - val2);
    }

    @Override
    public Byte multiply(Byte val1, Byte val2) {
        return (byte) (val1 * val2);
    }

    @Override
    public Byte divide(Byte val1, Byte val2) {
        return (byte) (val1 / val2);
    }

    @Override
    public Byte unaryMinus(Byte val) {
        return (byte) (-val);
    }

    @Override
    public Byte intConversion(int val) {
        return (byte) val;
    }
}
