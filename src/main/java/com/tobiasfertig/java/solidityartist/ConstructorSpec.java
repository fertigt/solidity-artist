package com.tobiasfertig.java.solidityartist;

public class ConstructorSpec implements Writable
{
	public final static String CONSTRUCTOR_KEYWORD = "constructor";

	private VisibilityName visibility;

	private ConstructorSpec( Builder builder )
	{
		this.visibility = builder.visibility;
	}

	public void write( CodeWriter writer )
	{
		writer.writeAndIndent( CONSTRUCTOR_KEYWORD )
			  .emptyBraces( )
			  .space( )
			  .write( this.visibility )
			  .space( )
			  .emptyCurlyBraces( );
	}

	public static Builder builder( VisibilityName visibility )
	{
		return new Builder( visibility );
	}

	public static final class Builder
	{
		private final VisibilityName visibility;

		private Builder( VisibilityName visibility )
		{
			this.visibility = visibility;
		}

		public ConstructorSpec build( )
		{
			return new ConstructorSpec( this );
		}
	}
}
