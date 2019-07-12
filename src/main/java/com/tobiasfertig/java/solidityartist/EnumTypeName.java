package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnumTypeName extends TypeName
{
	private final String name;
	private final List<String> values;

	public EnumTypeName( String name, List<String> values )
	{
		super( Keywords.ENUM.toString( ) );
		this.name = name;
		this.values = values;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.indent( );
		super.write( writer );
		writer.space( )
			  .write( this.name )
			  .space( )
			  .openCurlyBraces( );

		Iterator<String> iterator = values.iterator( );
		if ( iterator.hasNext( ) )
		{
			String nextValue = iterator.next( );
			while ( iterator.hasNext( ) )
			{
				writer.indent( )
					  .write( nextValue )
					  .comma( )
					  .newline( );

				nextValue = iterator.next( );
			}

			writer.indent( )
				  .write( nextValue );
		}

		writer.closeCurlyBraces( );
	}

	public static Builder builder( String name )
	{
		return new Builder( name );
	}

	public final static class Builder
	{
		private final String name;
		private final List<String> values = new ArrayList<>( );

		private Builder( String name )
		{
			this.name = name;
		}

		public Builder addValue( String value )
		{
			this.values.add( value );
			return this;
		}

		public EnumTypeName build( )
		{
			return new EnumTypeName( name, values );
		}
	}
}
