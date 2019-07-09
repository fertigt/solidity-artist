package com.tobiasfertig.java.solidityartist;

public class ConstructorSpec
{
	public final static String CONSTRUCTOR_KEYWORD = "constructor";

	private VisibilityName visibility;

	private ConstructorSpec( Builder builder )
	{
		this.visibility = builder.visibility;
	}

	public void write( CodeWriter writer )
	{
		writer.write( CONSTRUCTOR_KEYWORD )
			  .emptyBraces( )
			  .space( )
			  .write( this.visibility )
			  .space( )
			  .emptyCurlyBraces( );
	}

	public static final class Builder {
		private final VisibilityName visibility;

		private Builder(VisibilityName visibility)
		{
			this.visibility = visibility;
		}

	}
}
