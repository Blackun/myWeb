package com.blackun.util;

import java.nio.file.Path;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

public class MyFiles {
	public static String probeContentType(Path path) throws MagicParseException, MagicException, MagicMatchNotFoundException {
		Magic magic = new Magic();
		MagicMatch match = magic.getMagicMatch(path.toFile(), false);
		return match.getMimeType();
	}
}
