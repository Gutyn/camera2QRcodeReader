package com.qrcodereader;

import com.google.zxing.LuminanceSource;


/**
 * PlanarYUVLuminanceSource.class
 * Created by Mihail on 10/21/15 <mihail@breadwallet.com>.
 * Copyright (c) 2015 breadwallet llc.
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * This object extends LuminanceSource around an array of YUV data returned from
 * the camera driver, with the option to crop to a rectangle within the full
 * data. This can be used to exclude superfluous pixels around the perimeter and
 * speed up decoding. It works for any pixel format where the Y channel is
 * planar and appears first, including YCbCr_420_SP and YCbCr_422_SP.
 */
final public class PlanarYUVLuminanceSource extends LuminanceSource {

    private final byte[] mYuvData;

    public PlanarYUVLuminanceSource(byte[] yuvData, int width, int height) {
        super(width, height);

        mYuvData = yuvData;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
        final int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        final int offset = y * width;
        System.arraycopy(mYuvData, offset, row, 0, width);
        return row;
    }

    @Override
    public byte[] getMatrix() {
        return mYuvData;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

}
