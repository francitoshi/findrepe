/*
 *  FileComparatorByImage.java
 *
 *  Copyright (C) 2010-2026 francitoshi@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Report bugs or new features to: francitoshi@gmail.com
 */
package io.francitoshi.findrepe;

import io.nut.base.util.Hash;
import io.nut.headless.image.hash.ImageHashBuilder;
import io.nut.headless.io.virtual.VirtualFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.commons.compress.archivers.ArchiveException;

/**
 *
 * @author franci
 */
public class FileComparatorByImage implements Comparator<VirtualFile>
{
    private static final Map<VirtualFile,WeakReference<Hash>> map = Collections.synchronizedMap(new WeakHashMap<>());

    private final ImageHashBuilder ihb;


    public FileComparatorByImage(boolean half,boolean gray, int size, float colorThreshold, float countThreshold, boolean blur)
    {
        this.ihb  = new ImageHashBuilder(gray,size,colorThreshold, countThreshold);
    }

    private Hash getHash(VirtualFile pf) throws FileNotFoundException, IOException, ArchiveException
    {
        WeakReference<Hash> wr = map.get(pf);
        Hash fh = wr!=null?wr.get():null;
        if(fh==null)
        {
            fh = ihb.buildHash(pf);
            if(fh!=null)
            {
                map.put(pf, new WeakReference<>(fh));
            }
            //fh = new FileHash(pf);
        }
        return fh;
    }

    @Override
    public int compare(VirtualFile t, VirtualFile t1)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
