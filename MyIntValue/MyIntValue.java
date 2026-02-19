public class MyIntValue {
    public int value;

    public MyIntValue(int value) {
        this.value = value;
    }

    public MyIntValue() {
        this(1);
    }

    /** @return whether the value is even */
    public boolean isEven() {
        return (value % 2) == 0;
    }

    /** @return whether the value is odd */
    public boolean isOdd() {
        return !isEven();
    }

    /** @return whether the value is prime */
    public boolean isPrime() {
        // Non-positive numbers cant be prime. 1 is a special case and not considered a prime
        if(value <= 1) return false;
        if(value == 2) return true; // 2 won't work for the following algorithm, but it is a prime

        int factorCeiling = (int) Math.ceil(Math.sqrt(value));

        // Check all factors from 2 to the square root of the value, which is the "midpoint" of the factors of a number
        for(int possibleFactor = 2; possibleFactor <= factorCeiling; possibleFactor++) {
            if(value % possibleFactor == 0) return false;
        }

        return true;
    }

    /** Adds another MyIntValue to this
     * @param other The other MyIntValue
     * @return the sum
     */
    public MyIntValue add(MyIntValue other) {
        return new MyIntValue(value + other.value);
    }

    /** Subtract another MyIntValue from this
     * @param other The other MyIntValue
     * @return the difference
     */
    public MyIntValue sub(MyIntValue other) {
        return new MyIntValue(value - other.value);
    }

    /** Multiplies with another MyIntValue
     * @param other The other MyIntValue
     * @return the product
     */
    public MyIntValue times(MyIntValue other) {
        return new MyIntValue(value * other.value);
    }

    /** Divides by another MyIntValue. Rounds to the closest integer
     * @param other The other MyIntValue (divisor)
     * @return the quotient
     */
    public MyIntValue div(MyIntValue other) {
        double quotient = 1.0 * value / other.value;

        return new MyIntValue((int) Math.round(quotient));
    }
}