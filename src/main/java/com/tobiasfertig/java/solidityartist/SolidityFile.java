package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.elements.files.FileElement;
import com.tobiasfertig.java.solidityartist.visitors.FileVisitor;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SolidityFile
{
	private String fileName;
	private String path;
	private FileElement fileElement;

	private int lineLength;

	public SolidityFile( String fileName, String path,
		FileElement fileElement, int lineLength )
	{
		this.fileName = fileName;
		this.path = path.endsWith( "/" ) ? path : path + "/";
		this.fileElement = fileElement;
		this.lineLength = lineLength;
	}

	public void saveToFile( ) throws IOException
	{
		File file = new File( path );
		Files.createDirectories( file.toPath( ) );
		final FileWriter fw = new FileWriter( getFullFileName( ), StandardCharsets.UTF_8 );
		final BufferedWriter bufferedWriter = new BufferedWriter( fw );

		bufferedWriter.write( getFileElementAsString( ) );

		bufferedWriter.flush( );
		fw.flush( );
		fw.close( );
	}

	public void saveToOutputStream( OutputStream outputStream ) throws IOException
	{
		final OutputStreamWriter outputStreamWriter = new OutputStreamWriter( outputStream, StandardCharsets.UTF_8 );
		final BufferedWriter bufferedWriter = new BufferedWriter( outputStreamWriter );

		bufferedWriter.write( getFileElementAsString( ) );

		bufferedWriter.flush( );
		outputStreamWriter.flush( );
	}

	private String getFullFileName( )
	{
		return this.path + this.fileName;
	}

	private String getFileElementAsString( )
	{
		Visitor visitor = new FileVisitor( this.lineLength );
		this.fileElement.accept( visitor );
		return visitor.export( );
	}

	public String getFileName( )
	{
		return fileName;
	}

	public String getPath( )
	{
		return path;
	}

	public FileElement getFileElement( )
	{
		return fileElement;
	}
}
