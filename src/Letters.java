public enum Letters {


    a('0'),
    b('1'),

    c('2'
    ),
    d('3'),
    e('4'),
    f('5'),
    g('6'),
    h('7'),
    i('8'),

    //  i('8'),
    j('9');


    final int numberForLetter;

    Letters(int numberForLetter) {
        this.numberForLetter = numberForLetter;
    }


    public static String intToLetter(int y) {
        if (y == 0) {
            return "a";
        } else if (y == 1) {
            return "b";
        } else if (y == 2) {
            return "c";
        } else if (y == 3) {
            return "d";
        } else if (y == 4) {
            return "e";
        } else if (y == 5) {
            return "f";
        } else if (y == 6) {
            return "g";
        } else if (y == 7) {
            return "h";
        } else if (y == 8) {
            return "i";
        } else {
            return "j";
        }
    }
}
