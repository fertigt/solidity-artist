package com.tobiasfertig.java.solidityartist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CodeBlock implements Writable
{
	private final List<String> lines;

	private CodeBlock( Builder builder )
	{
		this.lines = builder.lines;
	}

	public boolean isEmpty( )
	{
		return this.lines.isEmpty( );
	}

	@Override public void write( CodeWriter writer )
	{
		Iterator<String> iterator = lines.iterator( );

		if ( iterator.hasNext( ) )
		{
			String line = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				writer.writeAndIndent( line )
					  .newline( );
				line = iterator.next( );
			}

			writer.writeAndIndent( line );
		}
	}

	public static Builder builder( )
	{
		return new Builder( );
	}

	public static CodeBlock emptyBlock( )
	{
		return builder( ).build( );
	}

	public final static class Builder
	{
		private final List<String> lines = new ArrayList<>( );

		private Builder( )
		{

		}

		public Builder addStatement( String line )
		{
			this.lines.add( line + ";" );
			return this;
		}

		public Builder addCode( String code )
		{
			String[] lines = code.split( "\n" );
			this.lines.addAll( Arrays.asList( lines ) );
			return this;
		}

		public CodeBlock build( )
		{
			return new CodeBlock( this );
		}
	}
}
