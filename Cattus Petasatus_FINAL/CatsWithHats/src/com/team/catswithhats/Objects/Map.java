package com.team.catswithhats.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.team.catswithhats.CatsWithHats;

public class Map {
	CatsWithHats game;
	public Texture mapImage;
	public int[] nodes = new int[55];

	public Map(Texture mapImage, CatsWithHats game) {
		this.mapImage = mapImage;
		this.game = game;
	}

	public void setNodes(int nodes[]) {

	}
}
