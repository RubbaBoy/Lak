package com.uddernetworks.lak.keys;

import java.util.Arrays;
import java.util.Optional;

/**
 * Key data comes from page 53 (Table 12) of the USB HID Usage Tables PDF
 */
public enum KeyEnum {
    KEY_A(4, 30),
    KEY_A_SHIFT(4, 30, true),
    KEY_B(5, 48),
    KEY_B_SHIFT(5, 48, true),
    KEY_C(6, 46),
    KEY_C_SHIFT(6, 46, true),
    KEY_D(7, 32),
    KEY_D_SHIFT(7, 32, true),
    KEY_E(8, 18),
    KEY_E_SHIFT(8, 18, true),
    KEY_F(9, 33),
    KEY_F_SHIFT(9, 33, true),
    KEY_G(10, 34),
    KEY_G_SHIFT(10, 34, true),
    KEY_H(11, 35),
    KEY_H_SHIFT(11, 35, true),
    KEY_I(12, 23),
    KEY_I_SHIFT(12, 23, true),
    KEY_J(13, 36),
    KEY_J_SHIFT(13, 36, true),
    KEY_K(14, 37),
    KEY_K_SHIFT(14, 37, true),
    KEY_L(15, 38),
    KEY_L_SHIFT(15, 38, true),
    KEY_M(16, 50),
    KEY_M_SHIFT(16, 50, true),
    KEY_N(17, 49),
    KEY_N_SHIFT(17, 49, true),
    KEY_O(18, 24),
    KEY_O_SHIFT(18, 24, true),
    KEY_P(19, 25),
    KEY_P_SHIFT(19, 25, true),
    KEY_Q(20, 16),
    KEY_Q_SHIFT(20, 16, true),
    KEY_R(21, 19),
    KEY_R_SHIFT(21, 19, true),
    KEY_S(22, 31),
    KEY_S_SHIFT(22, 31, true),
    KEY_T(23, 20),
    KEY_T_SHIFT(23, 20, true),
    KEY_U(24, 22),
    KEY_U_SHIFT(24, 22, true),
    KEY_V(25, 47),
    KEY_V_SHIFT(25, 47, true),
    KEY_W(26, 17),
    KEY_W_SHIFT(26, 17, true),
    KEY_X(27, 45),
    KEY_X_SHIFT(27, 45, true),
    KEY_Y(28, 21),
    KEY_Y_SHIFT(28, 21, true),
    KEY_Z(29, 44),
    KEY_Z_SHIFT(29, 44, true),
    KEY_1(30, 2),
    KEY_1_SHIFT(30, 2, true),
    KEY_2(31, 3),
    KEY_2_SHIFT(31, 3, true),
    KEY_3(32, 4),
    KEY_3_SHIFT(32, 4, true),
    KEY_4(33, 5),
    KEY_4_SHIFT(33, 5, true),
    KEY_5(34, 6),
    KEY_5_SHIFT(34, 6, true),
    KEY_6(35, 7),
    KEY_6_SHIFT(35, 7, true),
    KEY_7(36, 8),
    KEY_7_SHIFT(36, 8, true),
    KEY_8(37, 9),
    KEY_8_SHIFT(37, 9, true),
    KEY_9(38, 10),
    KEY_9_SHIFT(38, 10, true),
    KEY_0(39, 11),
    KEY_0_SHIFT(39, 11, true),
    KEY_ENTER(40, 28),
    KEY_ESC(41, 1),
    KEY_BACKSPACE(42, 14),
    KEY_TAB(43, 15),
    KEY_SPACE(44, 57),
    KEY_MINUS(45, 12),
    KEY_MINUS_SHIFT(45, 12, true),
    KEY_EQUAL(46, 13),
    KEY_EQUAL_SHIFT(46, 13, true),
    KEY_LEFTBRACE(47, 26),
    KEY_LEFTBRACE_SHIFT(47, 26, true),
    KEY_RIGHTBRACE(48, 27),
    KEY_RIGHTBRACE_SHIFT(48, 27, true),
    KEY_BACKSLASH(49, 43),
    KEY_BACKSLASH_SHIFT(49, 43, true),
    KEY_SEMICOLON(51, 39),
    KEY_SEMICOLON_SHIFT(51, 39, true),
    KEY_APOSTROPHE(52, 40),
    KEY_APOSTROPHE_SHIFT(52, 40, true),
    KEY_GRAVE(53, 41),
    KEY_GRAVE_SHIFT(53, 41, true),
    KEY_COMMA(54, 51),
    KEY_COMMA_SHIFT(54, 51, true),
    KEY_DOT(55, 52),
    KEY_DOT_SHIFT(55, 52, true),
    KEY_SLASH(56, 53),
    KEY_SLASH_SHIFT(56, 53, true),
    KEY_CAPSLOCK(57, 58),
    KEY_F1(58, 59),
    KEY_F2(59, 60),
    KEY_F3(60, 61),
    KEY_F4(61, 62),
    KEY_F5(62, 63),
    KEY_F6(63, 64),
    KEY_F7(64, 65),
    KEY_F8(65, 66),
    KEY_F9(66, 67),
    KEY_F10(67, 68),
    KEY_F11(68, 87),
    KEY_F12(69, 88),
    KEY_SYSRQ(70, 99),
    KEY_SCROLLLOCK(71, 70),
    KEY_PAUSE(72, 119),
    KEY_INSERT(73, 110),
    KEY_HOME(74, 102),
    KEY_PAGEUP(75, 104),
    KEY_DELETE(76, 111),
    KEY_END(77, 107),
    KEY_PAGEDOWN(78, 109),
    KEY_RIGHT(79, 106),
    KEY_LEFT(80, 105),
    KEY_DOWN(81, 108),
    KEY_UP(82, 103),
    KEY_NUMLOCK(83, 69),
    KEY_KPSLASH(84, 98),
    KEY_KPASTERISK(85, 55),
    KEY_KPMINUS(86, 74),
    KEY_KPPLUS(87, 78),
    KEY_KPENTER(88, 96),
    KEY_KP1(89, 79),
    KEY_KP2(90, 80),
    KEY_KP3(91, 81),
    KEY_KP4(92, 75),
    KEY_KP5(93, 76),
    KEY_KP6(94, 77),
    KEY_KP7(95, 71),
    KEY_KP8(96, 72),
    KEY_KP9(97, 73),
    KEY_KP0(98, 82),
    KEY_KPDOT(99, 83),
    KEY_102ND(100, 86),
    KEY_COMPOSE(101, 127),
    KEY_POWER(102, 116),
    KEY_KPEQUAL(103, 117),
    KEY_F13(104, 183),
    KEY_F14(105, 184),
    KEY_F15(106, 185),
    KEY_F16(107, 186),
    KEY_F17(108, 187),
    KEY_F18(109, 188),
    KEY_F19(110, 189),
    KEY_F20(111, 190),
    KEY_F21(112, 191),
    KEY_F22(113, 192),
    KEY_F23(114, 193),
    KEY_F24(115, 194),
    KEY_OPEN(116, 134),
    KEY_HELP(117, 138),
    KEY_MENU(118, 139),
    KEY_FRONT(119, 132),
    KEY_STOP(120, 128),
    KEY_AGAIN(121, 129),
    KEY_UNDO(122, 131),
    KEY_CUT(123, 137),
    KEY_COPY(124, 133),
    KEY_PASTE(125, 135),
    KEY_FIND(126, 136),
    KEY_MUTE(127, 113),
    KEY_VOLUMEUP(128, 115),
    KEY_VOLUMEDOWN(129, 114),
    KEY_KPCOMMA(133, 121),
    KEY_RO(135, 89),
    KEY_KATAKANAHIRAGANA(136, 93),
    KEY_YEN(137, 124),
    KEY_HENKAN(138, 92),
    KEY_MUHENKAN(139, 94),
    KEY_KPJPCOMMA(140, 95),
    KEY_HANGEUL(144, 122),
    KEY_HANJA(145, 123),
    KEY_KATAKANA(146, 90),
    KEY_HIRAGANA(147, 91),
    KEY_ZENKAKUHANKAKU(148, 85),
    KEY_KPLEFTPAREN(182, 179),
    KEY_KPRIGHTPAREN(183, 180),
    KEY_LEFTCTRL(224, 29),
    KEY_SHIFT(225, 42),
//    KEY_LEFTSHIFT(225, 42),
    KEY_LEFTALT(226, 56),
    KEY_LEFTMETA(227, 125),
    KEY_RIGHTCTRL(228, 97),
    KEY_RIGHTSHIFT(229, 54),
    KEY_RIGHTALT(230, 100),
    KEY_RIGHTMETA(231, 126),
    KEY_PLAYPAUSE(232, 164),
    KEY_STOPCD(233, 166),
    KEY_PREVIOUSSONG(234, 165),
    KEY_NEXTSONG(235, 163),
    KEY_EJECTCD(236, 161),
    KEY_WWW(240, 150),
    KEY_BACK(241, 158),
    KEY_FORWARD(242, 159),
    KEY_SCROLLUP(245, 177),
    KEY_SCROLLDOWN(246, 178),
    KEY_EDIT(247, 176),
    KEY_SLEEP(248, 142),
    KEY_SCREENLOCK(249, 152),
    KEY_REFRESH(250, 173),
    KEY_CALC(251, 140),
    ;

    private final int id;
    private final boolean shift;
    private final int linuxCode;

    KeyEnum(int id, int linuxCode) {
        this(id, linuxCode, false);
    }

    KeyEnum(int id, int linuxCode, boolean shift) {
        this.id = id;
        this.linuxCode = linuxCode;
        this.shift = shift;
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

    public static Optional<KeyEnum> fromId(int keyId, boolean shift) {
        return Arrays.stream(values()).filter(key -> key.shift == shift && key.id == keyId).findFirst();
    }

    public static Optional<KeyEnum> fromLinuxCode(int linuxCode, boolean shift) {
        return Arrays.stream(values()).filter(key -> key.shift == shift && key.linuxCode == linuxCode).findFirst();
    }
}
