package ca.hullabaloo.cdb;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CdbTest {
  @Rule
  public TemporaryFolder tmp = new TemporaryFolder();

  private static byte[] DUMMY_KEY = {};

  @Test
  public void notLeakFileDescriptor() throws IOException {
    for (int i = 0; i < 10000; ++i) {
      File f = tmp.newFile();
      buildEmptyMapAndDispose(f);
      if (f.exists()) {
        f.delete();
      }
    }
  }

  private void buildEmptyMapAndDispose(File f) throws IOException {
    Cdb.Builder b = Cdb.builder(f);
    b.close();
    Map<ByteBuffer, ByteBuffer> m = Cdb.open(f);
    m.get(ByteBuffer.wrap(DUMMY_KEY));
  }
}

// vim:set sts=2 sw=2 tw=0 et:
