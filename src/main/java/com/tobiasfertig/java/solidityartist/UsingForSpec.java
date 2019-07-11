package com.tobiasfertig.java.solidityartist;

public class UsingForSpec implements Writable
{
	public final static String USING_KEYWORD = "using";
	public final static String FOR_KEYWORD = "for";

	private final TypeName extension;
	private final TypeName source;

	private UsingForSpec( Builder builder )
	{
		this.extension = builder.extension;
		this.source = builder.source;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.writeAndIndent( USING_KEYWORD )
			  .space( )
			  .write( extension )
			  .space( )
			  .write( FOR_KEYWORD )
			  .space( )
			  .write( source )
			  .semicolon( );
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
