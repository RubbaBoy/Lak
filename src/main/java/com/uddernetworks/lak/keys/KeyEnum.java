package com.uddernetworks.lak.keys;

import java.util.Arrays;
import java.util.Optional;

/**
 * Key data comes from page 53 (Table 12) of the USB HID Usage Tables PDF
 */
public enum KeyEnum {
    RESERVED                  (0  ,       0),
    ERO                       (1  ,       0),
    POST_FAIL                 (2  ,       0),
    ERR_UNDEF                 (3  ,       0),
    A                         (4  ,       30),
    B                         (5  ,       48),
    C                         (6  ,       46),
    D                         (7  ,       32),
    E                         (8  ,       18),
    F                         (9  ,       33),
    G                         (10 ,       34),
    H                         (11 ,       35),
    I                         (12 ,       23),
    J                         (13 ,       36),
    K                         (14 ,       37),
    L                         (15 ,       38),
    M                         (16 ,       50),
    N                         (17 ,       49),
    O                         (18 ,       24),
    P                         (19 ,       25),
    Q                         (20 ,       16),
    R                         (21 ,       19),
    S                         (22 ,       31),
    T                         (23 ,       20),
    U                         (24 ,       22),
    V                         (25 ,       47),
    W                         (26 ,       17),
    X                         (27 ,       45),
    Y                         (28 ,       21),
    Z                         (29 ,       44),
    NUM_1                     (30 ,       2),
    ESCLAMATION               (30 , true, 0),
    NUM_2                     (31 ,       3),
    AT                        (31 , true, 0),
    NUM_3                     (32 ,       4),
    HASH                      (32 , true, 0),
    NUM_4                     (33 ,       5),
    DOLLAR                    (33 , true, 0),
    NUM_5                     (34 ,       6),
    PERCENT                   (34 , true, 0),
    NUM_6                     (35 ,       7),
    CARROT                    (35 , true, 0),
    NUM_7                     (36 ,       8),
    AND                       (36 , true, 0),
    NUM_8                     (37 ,       9),
    ASTERISK                  (37 , true, 0),
    NUM_9                     (38 ,       10),
    LEFT_PARENTHESE           (38 , true, 0),
    NUM_0                     (39 ,       11),
    RIGHT_PARENTHESE          (39 , true, 0),
    ENTER                     (40 ,       28),
    ESCAPE                    (41 ,       1),
    BACKSPACE                 (42 ,       14),
    TAB                       (43 ,       15),
    SPACE                     (44 ,       57),
    DASH                      (45 ,       12),
    UNDERSCORE                (45 , true, 0),
    EQUALS                    (46 ,       13),
    PLUS                      (46 , true, 0),
    LEFT_SQUARE_BRACKET       (47 ,       26),
    LEFT_CURLY_BRACKET        (47 , true, 26),
    RIGHT_SQUARE_BRACKET      (48 ,       27),
    RIGHT_CURLY_BRACKET       (48 , true, 27),
    BACKSLASH                 (49 ,       43),
    PIPE                      (49 , true, 43),
    NON_US_HASH               (50 ,       0),
    NON_US_TILDE              (50 , true, 0),
    SEMICOLON                 (51 ,       39),
    COLON                     (51 , true, 39),
    APOSTROPHE                (52 ,       40),
    QUOTES                    (52 , true, 40),
    GRAVE                     (53 ,       41),
    TILDE                     (53 , true, 41),
    COMMA                     (54 ,       51),
    LESS_THAN                 (54 , true, 51),
    PERIOD                    (55 ,       52),
    GREATER_THAN              (55 , true, 52),
    FORWARD_SLASH             (56 ,       53),
    QUESTION                  (56 , true, 53),
    CAPS_LOCK                 (57 ,       58),
    F1                        (58 ,       59),
    F2                        (59 ,       60),
    F3                        (60 ,       61),
    F4                        (61 ,       62),
    F5                        (62 ,       63),
    F6                        (63 ,       64),
    F7                        (64 ,       65),
    F8                        (65 ,       66),
    F9                        (66 ,       67),
    F10                       (67 ,       68),
    F11                       (68 ,       0),
    F12                       (69 ,       0),
    PRINT_SCREEN              (70 ,       0),
    SCROLL_LOCK               (71 ,       70),
    PAUSE                     (72 ,       0),
    INSERT                    (73 ,       0),
    HOME                      (74 ,       0),
    PAGE_UP                   (75 ,       0),
    DELETE                    (76 ,       0),
    END                       (77 ,       0),
    PAGE_DOWN                 (78 ,       0),
    ARROW_RIGHT               (79 ,       0),
    ARROW_LEFT                (80 ,       0),
    ARROW_DOWN                (81 ,       0),
    ARROW_UP                  (82 ,       0),
    NUM_LOCK                  (83 ,       69), // Also "Clear"
    KEYPAD_FORWARD_SLASH      (84 ,       0),
    KEYPAD_ASTERISK           (85 ,       55), // Is this KEY_KPASTERISK??
    KEYPAD_MINUS              (86 ,       74),
    KEYPAD_PLUS               (87 ,       78),
    KEYPAD_ENTER              (88 ,       0),
    KEYPAD_1                  (89 ,       79), // End
    KEYPAD_2                  (90 ,       80), // Down Arrow
    KEYPAD_3                  (91 ,       81), // Page Down
    KEYPAD_4                  (92 ,       75), // Left Arrow
    KEYPAD_5                  (93 ,       76),
    KEYPAD_6                  (94 ,       77), // Right Arrow
    KEYPAD_7                  (95 ,       71), // Home
    KEYPAD_8                  (96 ,       72), // Up Arrow
    KEYPAD_9                  (97 ,       73), // Page Up
    KEYPAD_0                  (98 ,       82), // Insert
    KEYPAD_PERIOD             (99 ,       83), // Delete
    NON_US_BACKSLASH          (100,       0),
    NON_US_PIPE               (100, true, 0),
    APPLICATION               (101,       0),
    POWER                     (102,       0),
    KEYPAD_EQUALS             (103,       0),
    F13                       (104,       0),
    F14                       (105,       0),
    F15                       (106,       0),
    F16                       (107,       0),
    F17                       (108,       0),
    F18                       (109,       0),
    F19                       (110,       0),
    F20                       (111,       0),
    F21                       (112,       0),
    F22                       (113,       0),
    F23                       (114,       0),
    F24                       (115,       0),
    EXECUTE                   (116,       0),
    HELP                      (117,       0),
    MENU                      (118,       0),
    SELECT                    (119,       0),
    STOP                      (120,       0),
    AGAIN                     (121,       0),
    UNDO                      (122,       0),
    CUT                       (123,       0),
    COPY                      (124,       0),
    PASTE                     (125,       0),
    FIND                      (126,       0),
    MUTE                      (127,       0),
    VOLUME_UP                 (128,       0),
    VOLUME_DOWN               (129,       0),
    LOCKING_CAPS_LOCK         (130,       0),
    LOCKING_NUM_LOCK          (131,       0),
    LOCKING_SCROLL_LOCK       (132,       0),
    KEYPAD_BRAZILIAN_COMMA    (133,       0), // Keypad Comma is the appropriate usage for the Brazilian keypad period (.) key.
    // This represents the closest possible match, and system software should do the correct mapping based on the
    // current locale setting.
    KEYPAD_AS400_EQUALS       (134,       0), // Used on AS/400 keyboards
    INTERNATIONAL_1           (135,       0),
    INTERNATIONAL_2           (136,       0),
    INTERNATIONAL_3           (137,       0),
    INTERNATIONAL_4           (138,       0),
    INTERNATIONAL_5           (139,       0),
    INTERNATIONAL_6           (140,       0),
    INTERNATIONAL_7           (141,       0),
    INTERNATIONAL_8           (142,       0),
    INTERNATIONAL_9           (143,       0),
    LANG_1                    (144,       0),
    LANG_2                    (145,       0),
    LANG_3                    (146,       0),
    LANG_4                    (147,       0),
    LANG_5                    (148,       0),
    LANG_6                    (149,       0),
    LANG_7                    (150,       0),
    LANG_8                    (151,       0),
    LANG_9                    (152,       0),
    ALTERNATE_ERASE           (153,       0),
    SYSREQ_ATTENTION          (154,       0),
    CANCEL                    (155,       0),
    CLEAR                     (156,       0),
    PRIOR                     (157,       0),
    RETURN                    (158,       0),
    SEPARATOR                 (159,       0),
    OUT                       (160,       0),
    OPER                      (161,       0),
    CLEAR_AGAIN               (162,       0), // Clear/Again
    CRSEL_PROPS               (163,       0),
    EXSEL                     (164,       0),
    // Reserved
    RESERVED_165              (165,       0),
    RESERVED_166              (166,       0),
    RESERVED_167              (167,       0),
    RESERVED_168              (168,       0),
    RESERVED_169              (169,       0),
    RESERVED_170              (170,       0),
    RESERVED_171              (171,       0),
    RESERVED_172              (172,       0),
    RESERVED_173              (173,       0),
    RESERVED_174              (174,       0),
    RESERVED_175              (175,       0),
    KEYPAD_00                 (176,       0),
    KEYPAD_000                (177,       0),
    THOUSANDS_SEPARATOR       (178,       0),
    DECIMAL_SEPARATOR         (179,       0),
    CURRENCY_UNIT             (180,       0),
    CURRENCY_SUB_UNIT         (181,       0),
    KEYPAD_LEFT_PARENTHESE    (182,       0),
    KEYPAD_RIGHT_PARENTHESE   (183,       0),
    KEYPAD_LEFT_CURLY_BRACKET (184,       0),
    KEYPAD_RIGHT_CURLY_BRACKET(185,       0),
    KEYPAD_TAB                (186,       0),
    KEYPAD_BACKSPACE          (187,       0),
    KEYPAD_A                  (188,       0),
    KEYPAD_B                  (189,       0),
    KEYPAD_C                  (190,       0),
    KEYPAD_D                  (191,       0),
    KEYPAD_E                  (192,       0),
    KEYPAD_F                  (193,       0),
    KEYPAD_XOR                (194,       0),
    KEYPAD_CARROT             (195,       0),
    KEYPAD_PERCENT            (196,       0),
    KEYPAD_LESS_THAN          (197,       0),
    KEYPAD_GREATER_THAN       (198,       0),
    KEYPAD_AND                (199,       0),
    KEYPAD_AND_AND            (200,       0),
    KEYPAD_PIPE               (201,       0),
    KEYPAD_PIPE_PIPE          (202,       0),
    KEYPAD_COLON              (203,       0),
    KEYPAD_HASH               (204,       0),
    KEYPAD_SPACE              (205,       0),
    KEYPAD_AT                 (206,       0),
    KEYPAD_EXCLAMATION        (207,       0),
    KEYPAD_MEM_STORE          (208,       0),
    KEYPAD_MEM_RECALL         (209,       0),
    KEYPAD_MEM_CLEAR          (210,       0),
    KEYPAD_MEM_ADD            (211,       0),
    KEYPAD_MEM_SUBTRACT       (212,       0),
    KEYPAD_MEM_MULTIPLY       (213,       0),
    KEYPAD_MEM_DIVIDE         (214,       0),
    KEYPAD_PLUS_MINUS         (215,       0), // +/-
    KEYPAD_CLEAR              (216,       0),
    KEYPAD_CLEAR_ENTRY        (217,       0),
    KEYPAD_BINARY             (218,       0),
    KEYPAD_OCTAL              (219,       0),
    KEYPAD_DECIMAL            (220,       0),
    KEYPAD_HEXADECIMAL        (221,       0),
    RESERVED_222              (222,       0),
    RESERVED_223              (223,       0),
    LEFT_CONTROL              (224,       29),
    LEFT_SHIFT                (225,       42),
    LEFT_ALT                  (226,       56),
    LEFT_GUI                  (227,       0),
    RIGHT_CONTROL             (228,       0),
    RIGHT_SHIFT               (229,       54),
    RIGHT_ALT                 (230,       0),
    RIGHT_GUI                 (231,       0),
    // Reserved 0xE8 - 0xFFFF
    ;

    private final int id;
    private final boolean shift;
    private final int linuxCode;

    KeyEnum(int id) {
        this(id, false, 0);
    }

    KeyEnum(int id, int linuxCode) {
        this(id, false, linuxCode);
    }

    KeyEnum(int id, boolean shift, int linuxCode) {
        this.id = id;
        this.shift = shift;
        this.linuxCode = linuxCode;
    }

    KeyEnum(int id, boolean shift) {
        this(id, shift, 0);
    }

    public int getId() {
        return id;
    }

    public boolean isShift() {
        return shift;
    }

    public int getLinuxCode() {
        return linuxCode;
    }

    public static Optional<KeyEnum> fromId(int keyId) {
        return Arrays.stream(values()).filter(key -> key.id == keyId).findFirst();
    }

    public static Optional<KeyEnum> fromLinuxCode(int linuxCode) {
        return Arrays.stream(values()).filter(key -> key.linuxCode == linuxCode).findFirst();
    }
}
