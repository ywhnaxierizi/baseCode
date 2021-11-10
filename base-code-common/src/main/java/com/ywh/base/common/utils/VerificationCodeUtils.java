package com.ywh.base.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * @author ywh
 * @description 验证码工具类
 * @Date 2021/11/10 14:24
 */
public class VerificationCodeUtils {

    /**去掉容易混淆的字符1 0 I O*/
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static Random random = new Random();

    /**
     * 指定验证码长度获取验证码
     * @param verifySize
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }


    /**
     * 生成验证码输出流，返回验证码
     * @param w
     * @param h
     * @param os
     * @param verifySize
     * @return
     */
    public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    /**
     * 生成验证码文件，返回验证码
     * @param w
     * @param h
     * @param file
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, File file, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, file, verifyCode);
        return verifyCode;
    }


    /**
     *生成验证码文件
     * @param w
     * @param h
     * @param file
     * @param code
     */
    public static void outputImage(int w, int h, File file, String code) {
        if (file == null) {
            return;
        }
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            outputImage(w, h, fos, code);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据指定源生成指定长度的验证码code
     * @param verifySize 生成的验证码长度
     * @param sources 验证码字符源
     * @return 验证码字符
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (StringUtils.isBlank(sources)) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        //使用System.currentTimeMillis()作为随机值，每次生成verifyCode时取值都是随机的
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i=0;i<verifySize;i++) {
            verifyCode.append(sources.charAt(random.nextInt(codesLen)));
        }
        return verifyCode.toString();
    }

    /**
     * 验证码生成图片
     * @param h 图片高
     * @param w 图片宽
     * @param os 输出流
     * @param code 验证码
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random ran1 = new Random();
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorsSpaces = new Color[] {Color.WHITE,Color.CYAN,Color.GRAY,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i=0;i<colors.length;i++) {
            colors[i] = colorsSpaces[ran1.nextInt(colorsSpaces.length)];
            fractions[i] = ran1.nextFloat();
        }
        Arrays.sort(fractions);

        //设置边框色
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, w, h);
        Color c = getRandColor(200, 250);
        //设置背景色
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 2, w, h-4);

        //绘制干扰线
        Random ran2 = new Random();
        graphics2D.setColor(getRandColor(160, 200));
        graphics2D.setFont(getRandFont(h - 15));
        for (int i = 0; i < 20; i++) {
            int x = ran2.nextInt(w - 1);
            int y = ran2.nextInt(h - 1);
            int x1 = ran2.nextInt(6) + 1;
            int y1 = ran2.nextInt(12) + 1;
            graphics2D.setStroke(new BasicStroke(10.0f));
            graphics2D.drawLine(x, y, x + x1 + 400, y + y1 + 20);
        }

        //添加噪点
        float yawpRate = 0.05f;
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = ran2.nextInt(w);
            int y = ran2.nextInt(h);
            int rgb = getRandIntColor();
            image.setRGB(x, y, rgb);
        }

        //图片扭曲

        shear(graphics2D, w, h, c);

        graphics2D.setColor(getRandColor(100, 160));
        int fontSize = h - 15;
        graphics2D.setFont(getRandFont(fontSize));
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * ran1.nextDouble() * (ran1.nextBoolean() ? 1 : -1), (w /verifySize) * i + fontSize / 2, h /2);
            graphics2D.setTransform(affine);
            graphics2D.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
        }
        graphics2D.dispose();
        ImageIO.write(image, "png", os);
    }

    /**
     * 随机获取颜色对象
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandIntColor() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static Font getRandFont(int fontSize) {
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        try {
            int rand = (int) Math.round(Math.random()*7 + 1);
            switch (rand) {
                case 1:
                    font = new Font("Algerian", Font.ITALIC, fontSize);
                    break;
                case 2:
                    font = new Font("DIALOG", Font.ITALIC, fontSize);
                    break;
                case 3:
                    font = new Font("Impact", Font.ITALIC, fontSize);
                    break;
                case 4:
                    break;
                case 5:
                    font = new Font("幼圆", Font.ITALIC, fontSize);
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    font = new Font("Niagara Solid", Font.ITALIC, fontSize);
                    break;
                default:
                    new Font("Algerian", Font.ITALIC, fontSize);
                    break;
            }
        } catch (Exception e) {}
        return font;
    }


    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    /**
     * 是图片横向扭曲
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }


    /**
     * 是图片纵向扭曲
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;

        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d , i, 0);
                g.drawLine(i, (int)d + h1,i, h1);
            }
        }
    }

    public static void main(String[] args) {
        String verifyCode = generateVerifyCode(4);
        System.out.println(verifyCode);
        String fileName = "D:\\test\\verifyCode.jpg";
        File file = new File(fileName);
        outputImage(80,50,file, verifyCode);
    }

}
