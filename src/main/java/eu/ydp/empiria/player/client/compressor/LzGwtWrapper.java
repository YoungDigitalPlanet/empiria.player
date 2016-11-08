package eu.ydp.empiria.player.client.compressor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.compressor.converters.BytesToHexConverter;
import eu.ydp.empiria.player.client.compressor.converters.HexToByteConverter;
import eu.ydp.gwtutil.client.debug.log.Logger;
import org.dellroad.lzma.client.LZMAByteArrayCompressor;
import org.dellroad.lzma.client.LZMAByteArrayDecompressor;
import org.dellroad.lzma.client.UTF8;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class LzGwtWrapper {

    private static final String ERROR_MESSAGE = "Unable to decompress state ";

    private final HexToByteConverter hexToByteConverter;
    private final BytesToHexConverter bytesToHexConverter;
    private final Logger logger;

    @Inject
    public LzGwtWrapper(HexToByteConverter hexToByteConverter, BytesToHexConverter bytesToHexConverter, Logger logger) {
        this.hexToByteConverter = hexToByteConverter;
        this.bytesToHexConverter = bytesToHexConverter;
        this.logger = logger;
    }

    public String decompress(String compressedString) {
        byte[] bytes = hexToByteConverter.convert(compressedString);

        try {
            LZMAByteArrayDecompressor decompressor = new LZMAByteArrayDecompressor(bytes);
            while (decompressor.execute()) {}
            byte[] uncompressedData = decompressor.getUncompressedData();

            return new String(uncompressedData);
        } catch (IOException e) {
            logger.error(ERROR_MESSAGE + compressedString);
        }

        return "";
    }

    public String compress(String valueToCompress) {

        LZMAByteArrayCompressor lzmaByteArrayCompressor = new LZMAByteArrayCompressor(UTF8.encode(valueToCompress));

        while (lzmaByteArrayCompressor.execute()) {
        }
        byte[] compressedData = lzmaByteArrayCompressor.getCompressedData();
        return bytesToHexConverter.convert(compressedData);
    }
}