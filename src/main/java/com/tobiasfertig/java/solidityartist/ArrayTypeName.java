package com.tobiasfertig.java.solidityartist;

import java.util.ArrayList;
import java.util.List;

public class ArrayTypeName extends TypeName
{
	private final List<Integer> dimensionSizes;

	public ArrayTypeName( TypeName arrayType, List<Integer> dimensionSizes )
	{
		super( arrayType );
		this.dimensionSizes = dimensionSizes;
	}

	@Override public void write( CodeWriter writer )
	{
		super.write( writer );
		for ( Integer dimensionSize : dimensionSizes )
		{
			writer.write( "[" );

			if ( dimensionSize > 0 )
			{
				writer.write( dimensionSize.toString( ) );
			}

			writer.write( "]" );
		}
	}

	public static Builder builder( TypeName arrayType )
	{
		return new Builder( arrayType );
	}

	public final static class Builder
	{
		private final TypeName arrayType;
		private final List<Integer> dimensionSizes = new ArrayList<>( );

		private Builder( TypeName arrayType )
		{
			this.arrayType = arrayType;
		}

		/**
		 * This method allows to add dimensions to the array type.
		 * @param size contains the size of the next dimension. A static array has a size greater than zero. If the
		 *                   size is equal to zero, a dynamic array will be created.
		 * @return the Builder itself for method chaining.
		 */
		public Builder addDimension( int size )
		{
			dimensionSizes.add( size );
			return this;
		}

		public ArrayTypeName build( )
		{
			return new ArrayTypeName( arrayType, dimensionSizes );
		}
	}
}
