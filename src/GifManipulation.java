import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import javax.swing.*;

public class GifManipulation{
	private List<BufferedImage> imgList = new ArrayList<BufferedImage>();
	private long delayTime = 0;
	Color c = new Color(0,0,0,0);
	
	public void readGif(String filename) {
	    try {
	        ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
	        reader.setInput(ImageIO.createImageInputStream(new FileInputStream(filename)));
	        int i = reader.getMinIndex();
	        int numImages = reader.getNumImages(true);
	        while (i < numImages)
	        {
	        	imgList.add(reader.read(i++));
	        }
	        IIOMetadata imageMetaData =  reader.getImageMetadata(0);
	        String metaFormatName = imageMetaData.getNativeMetadataFormatName();

	        IIOMetadataNode root = (IIOMetadataNode)imageMetaData.getAsTree(metaFormatName);

	        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
	        delayTime = Long.valueOf(graphicsControlExtensionNode.getAttribute("delayTime").toString());
	        System.out.println(graphicsControlExtensionNode.getAttribute("delayTime"));
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName){
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)== 0) {
            return((IIOMetadataNode) rootNode.item(i));
            }
       }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }
    
    public void printGif(Graphics g, int x,int y) throws InterruptedException {
    	for(int i = 0;i < imgList.size();i++) {
    		g.drawImage(imgList.get(i), x, y, null);
    	}
    }
	
}
