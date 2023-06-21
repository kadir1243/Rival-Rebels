/*******************************************************************************
 * Copyright (c) 2012, 2016 Rodol Phito.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Mozilla Public License Version 2.0
 * which accompanies this distribution, and is available at
 * https://www.mozilla.org/en-US/MPL/2.0/
 *
 * Rival Rebels Mod. All code, art, and design by Rodol Phito.
 *
 * http://RivalRebels.com/
 *******************************************************************************/
package assets.rivalrebels.common.core;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;

public class FileRW
{
	public static String read(File file)
	{
		StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (Exception e) {
        }
		return text.toString();
	}

	public static byte[] readbytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public static void write(File file, String text)
	{
        try (Writer output = new BufferedWriter(new FileWriter(file))) {
            output.write(text);
        } catch (IOException e) {
        }
	}

	public static void writebytes(File file, byte[] data)
	{
		FileOutputStream fos = null;
		try
		{
			file.delete();
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(data);
		}
		catch (Exception e) {}
		finally
		{
			try
			{
				if (fos!=null) fos.close();
			}
			catch (IOException e) {}
		}
	}

    public static byte[] getBytesString(String str)
	{
		char[] buffer = str.toCharArray();
		byte[] bytes = new byte[buffer.length];
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) buffer[i];
		}
		return bytes;
	}

	public static String getStringBytes(byte[] bytes)
	{
		char[] buffer = new char[bytes.length];
		for (int i = 0; i < bytes.length; i++)
		{
			buffer[i] = (char) bytes[i];
			if (buffer[i] == 'ﾧ') buffer[i] = '§';
		}

		return new String(buffer);
	}
}
