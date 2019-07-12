package com.tobiasfertig.java.solidityartist;

import com.tobiasfertig.java.solidityartist.utils.Keywords;

import java.util.Iterator;

public class FunctionTypeName extends TypeName
{
	private final FunctionSpec function;

	public FunctionTypeName( FunctionSpec function )
	{
		super( Keywords.FUNCTION.toString( ) );
		this.function = function;
	}

	@Override public void write( CodeWriter writer )
	{
		super.write( writer );
		writer.openBraces( );

		if ( !this.function.getParameters( ).isEmpty( ) )
		{
			Iterator<ParameterSpec> iterator = this.function.getParameters( ).iterator( );
			ParameterSpec nextParameter = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				writer.write( nextParameter.getType( ) )
					  .comma( )
					  .space( );

				nextParameter = iterator.next( );
			}

			writer.write( nextParameter.getType( ) );
		}

		writer.closeBraces( )
			  .space( )
			  .write( function.getVisibility( ) );

		if ( !this.function.getModifiers( ).isEmpty( ) )
		{
			writer.space( )
				  .write( function.getModifiers( ) );

		}

		if ( !this.function.getReturnParameters( ).isEmpty( ) )
		{
			writer.space( )
				  .write( Keywords.RETURNS )
				  .openBraces( );

			Iterator<ParameterSpec> iterator = this.function.getReturnParameters( ).iterator( );
			ParameterSpec nextParameter = iterator.next( );

			while ( iterator.hasNext( ) )
			{
				writer.write( nextParameter.getType( ) )
					  .comma( )
					  .space( );

				nextParameter = iterator.next( );
			}

			writer.write( nextParameter.getType( ) )
				  .closeBraces( );
		}
	}
}
