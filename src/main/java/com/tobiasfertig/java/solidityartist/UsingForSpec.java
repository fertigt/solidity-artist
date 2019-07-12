package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

public class UsingForSpec implements Writable
{
	private final TypeName extension;
	private final TypeName source;

	private UsingForSpec( Builder builder )
	{
		this.extension = builder.extension;
		this.source = builder.source;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( Keywords.USING )
			  .space( )
			  .write( extension )
			  .space( )
			  .write( Keywords.FOR )
			  .space( )
			  .write( source )
			  .semicolonAndNewline( );
	}

	public static Builder builder( TypeName extension, TypeName source )
	{
		return new Builder( extension, source );
	}

	public static Builder builder( String extension, String source )
	{
		return new Builder( extension, source );
	}

	public final static class Builder
	{
		private final TypeName extension;
		private final TypeName source;

		private Builder( TypeName extension, TypeName source )
		{
			this.extension = extension;
			this.source = source;
		}

		private Builder( String extension, String source )
		{
			this.extension = new TypeName( extension );
			this.source = new TypeName( source );
		}

		public UsingForSpec build( )
		{
			return new UsingForSpec( this );
		}
	}
}
