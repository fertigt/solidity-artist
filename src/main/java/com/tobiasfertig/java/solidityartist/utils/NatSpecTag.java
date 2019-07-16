package com.tobiasfertig.java.solidityartist.utils;

public enum NatSpecTag
{
	AUTHOR("@author"),
	DEV( "@dev" ),
	NOTICE( "@notice" ),
	PARAM("@param"),
	RETURN( "@return" ),
	TITLE( "@title" );

	private final String tag;

	NatSpecTag( String tag )
	{
		this.tag = tag;
	}

	public String toString( )
	{
		return this.tag;
	}
}
