public class Color {
    private double red;
    private double green;
    private double blue;

    public static final Color RED = new Color(255, 0, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color MAGENTA = new Color(255, 0, 255);

    /**
     * Constructs a color from the given red, green, and blue values
     * @param red [0, 255]
     * @param green [0, 255]
     * @param blue [0, 255]
     */
    public Color(double red, double green, double blue) {
        this.red = Math.max(0, Math.min(red, 255));
        this.green = Math.max(0, Math.min(green, 255));
        this.blue = Math.max(0, Math.min(blue, 255));
    }

    /**
     * Constructs a color from the given hex value
     * @param hex The hex string as an integer (for example, #20feab would be 0x20feab)
     * @return The color
     */
    public static Color fromHex(int hex) {
        return new Color(
            (hex >> 16) & 0xFF,
            (hex >> 8) & 0xFF,
            hex & 0xFF
        );
    }

    /**
     * Constructs a color from the given hex string
     * @param hexString The string must be in the format #rrggbb (where rr, gg, bb are the red, green, and blue values as hexadecimal integers)
     * @return The color
     * @throws IllegalArgumentException An invalid hex string was provided
     */
    public static Color fromHex(String hexString) throws IllegalArgumentException {
        try {
            int hex = Integer.parseInt(hexString.substring(1, 7), 16);

            return Color.fromHex(hex);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Constructs a Color object from the given HSV values
     * @param hue Should be [0, 360), measured in degrees
     * @param saturation [0.0, 1.0], the saturation percentage as a decimal
     * @param value [0.0, 1.0], the value percentage as a decimal
     * @return A color with said HSV values
     */
    public static Color fromHSV(double hue, double saturation, double value) {
        hue = Math.max(0, hue) % 360;

        double chroma = saturation * value;
        double huePrime = hue / 60;
        double intermediate = chroma * (1 - Math.abs(huePrime % 2 - 1));

        double min = value - chroma;

        double red1 = 0;
        double green1 = 0;
        double blue1 = 0;

        if(huePrime < 1) {
            red1 = chroma;
            green1 = intermediate;
        } else if(huePrime < 2) {
            red1 = intermediate;
            green1 = chroma;
        } else if(huePrime < 3) {
            green1 = chroma;
            blue1 = intermediate;
        } else if(huePrime < 4) {
            green1 = intermediate;
            blue1 = chroma;
        } else if(huePrime < 5) {
            blue1 = chroma;
            red1 = intermediate;
        } else {
            blue1 = intermediate;
            red1 = chroma;
        }

        return new Color(
            255 * (red1 + min), 
            255 * (green1 + min), 
            255 * (blue1 + min)
        );
    }

    public Color lerp(Color otherColor, double alphaFactor) {
        double deltaRed = otherColor.red - this.red;
        double deltaGreen = otherColor.green - this.green;
        double deltaBlue = otherColor.blue - this.blue;

        return new Color(
            red + deltaRed * alphaFactor, 
            green + deltaGreen * alphaFactor, 
            blue + deltaBlue * alphaFactor
        );
    }

    public Color times(double scalar) {
        return new Color(
            red * scalar, 
            green * scalar, 
            blue * scalar
        );
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", // Have to use an bit-wise AND so that it displays as an unsigned byte
            red, 
            green, 
            blue
        );
    }

    public String hexString() {
        return String.format(
            "#%s", 
            Integer.toHexString(
                (((int) Math.round(red) & 0xff) << 16) + 
                (((int) Math.round(green) & 0xff) << 8) + 
                ((int) Math.round(blue) & 0xff)
            )
        );
    }
}
