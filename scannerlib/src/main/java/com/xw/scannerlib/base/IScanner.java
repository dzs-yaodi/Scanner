package com.xw.scannerlib.base;

public interface IScanner {

    public Result scan(byte[] data, int width, int height) throws Exception;

}
