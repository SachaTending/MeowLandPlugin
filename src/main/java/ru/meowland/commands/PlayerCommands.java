package ru.meowland.commands;

import arc.Core;
import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.game.Waves;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import org.yaml.snakeyaml.Yaml;

import java.util.HashSet;
import java.util.Map;

public class PlayerCommands {
    private static double ratio = 0.6;
    private HashSet<String> votes = new HashSet<>();
    private Map<String, Object> obj;
    private String shiza, rtv;
    public void shiza(String[] args, Player player){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        shiza = obj.get("shiza").toString();
        if((shiza.equals("false") && player.admin) || shiza.equals("true")){
            player.sendMessage(args[0]);
        }else{
            player.sendMessage("[red]Ты не админ");
            return;
        }
    }
    public void rtv(String[] args, Player player){
        Yaml yml = new Yaml();
        obj = yml.load(String.valueOf(Core.settings.getDataDirectory().child("/mods/MeowLand/config.yml").readString()));
        rtv = obj.get("rtv").toString();
        if((rtv.equals("false") && player.admin) || shiza.equals("true")){
            this.votes.add(player.uuid());
            int cur = this.votes.size();
            int req = (int) Math.ceil(ratio * Groups.player.size());
            Call.sendMessage("[navy]RTV[]: " + player.name + "[]голосует за смену карты [green]" + cur + "[] голос(ов;a), из [green]" + req);
            if(cur < req){
                return;
            }
            this.votes.clear();
            Call.sendMessage("[navy]RTV[]: голоса приняты, смена карты");
            Events.fire(new EventType.GameOverEvent(Team.crux));
        }else{
            player.sendMessage("[red]Ты не админ");
            return;
        }
    }
    public void wave(String[] args, Player player){
        Events.fire(new EventType.WaveEvent());
    }
}
