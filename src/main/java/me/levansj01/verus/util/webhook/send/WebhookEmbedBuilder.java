package me.levansj01.verus.util.webhook.send;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.discordjson.json.EmbedAuthorData;
import discord4j.discordjson.json.EmbedData;
import discord4j.discordjson.json.EmbedFooterData;
import discord4j.discordjson.json.EmbedImageData;
import discord4j.discordjson.json.EmbedThumbnailData;
import discord4j.discordjson.possible.Possible;
import java.awt.Color;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import me.levansj01.verus.util.webhook.send.WebhookEmbed.EmbedAuthor;
import me.levansj01.verus.util.webhook.send.WebhookEmbed.EmbedField;
import me.levansj01.verus.util.webhook.send.WebhookEmbed.EmbedFooter;
import me.levansj01.verus.util.webhook.send.WebhookEmbed.EmbedTitle;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.AuthorInfo;
import net.dv8tion.jda.api.entities.MessageEmbed.Footer;
import net.dv8tion.jda.api.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.api.entities.MessageEmbed.Thumbnail;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedImage;
import org.javacord.api.entity.message.embed.EmbedThumbnail;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WebhookEmbedBuilder {
   private EmbedAuthor author;
   private Integer color;
   private String thumbnailUrl;
   private EmbedTitle title;
   private String imageUrl;
   private final List<EmbedField> fields;
   private OffsetDateTime timestamp;
   private EmbedFooter footer;
   private String description;

   private boolean isEmpty(String var1) {
      return var1 == null || var1.trim().isEmpty();
   }

   @NotNull
   public static WebhookEmbedBuilder fromJavacord(@NotNull Embed var0) {
      WebhookEmbedBuilder var1 = new WebhookEmbedBuilder();
      var0.getTitle().ifPresent((var2) -> {
         var1.setTitle(new EmbedTitle(var2, (String)var0.getUrl().map(URL::toString).orElse((Object)null)));
      });
      var0.getDescription().ifPresent(var1::setDescription);
      var0.getTimestamp().ifPresent(var1::setTimestamp);
      var0.getColor().map(Color::getRGB).ifPresent(var1::setColor);
      var0.getFooter().map((var0x) -> {
         return new EmbedFooter((String)var0x.getText().orElseThrow(NullPointerException::new), (String)var0x.getIconUrl().map(URL::toString).orElse((Object)null));
      }).ifPresent(var1::setFooter);
      var0.getImage().map(EmbedImage::getUrl).map(URL::toString).ifPresent(var1::setImageUrl);
      var0.getThumbnail().map(EmbedThumbnail::getUrl).map(URL::toString).ifPresent(var1::setThumbnailUrl);
      var0.getFields().stream().map((var0x) -> {
         return new EmbedField(var0x.isInline(), var0x.getName(), var0x.getValue());
      }).forEach(var1::addField);
      return var1;
   }

   @NotNull
   public WebhookEmbedBuilder setDescription(@Nullable String var1) {
      this.description = var1;
      return this;
   }

   public WebhookEmbedBuilder(@Nullable WebhookEmbed var1) {
      this();
      if (var1 != null) {
         this.timestamp = var1.getTimestamp();
         this.color = var1.getColor();
         this.description = var1.getDescription();
         this.thumbnailUrl = var1.getThumbnailUrl();
         this.imageUrl = var1.getImageUrl();
         this.footer = var1.getFooter();
         this.title = var1.getTitle();
         this.author = var1.getAuthor();
         this.fields.addAll(var1.getFields());
      }

   }

   public void reset() {
      this.fields.clear();
      this.timestamp = null;
      this.color = null;
      this.description = null;
      this.thumbnailUrl = null;
      this.imageUrl = null;
      this.footer = null;
      this.title = null;
      this.author = null;
   }

   @NotNull
   public WebhookEmbedBuilder setThumbnailUrl(@Nullable String var1) {
      this.thumbnailUrl = var1;
      return this;
   }

   @NotNull
   public WebhookEmbedBuilder setTitle(@Nullable EmbedTitle var1) {
      this.title = var1;
      return this;
   }

   public WebhookEmbedBuilder() {
      this.fields = new ArrayList(10);
   }

   public boolean isEmpty() {
      return this.isEmpty(this.description) && this.isEmpty(this.imageUrl) && this.isEmpty(this.thumbnailUrl) && this.isFieldsEmpty() && this.isAuthorEmpty() && this.isTitleEmpty() && this.isFooterEmpty() && this.timestamp == null;
   }

   @NotNull
   public static WebhookEmbedBuilder fromJDA(@NotNull MessageEmbed var0) {
      WebhookEmbedBuilder var1 = new WebhookEmbedBuilder();
      String var2 = var0.getUrl();
      String var3 = var0.getTitle();
      String var4 = var0.getDescription();
      Thumbnail var5 = var0.getThumbnail();
      AuthorInfo var6 = var0.getAuthor();
      Footer var7 = var0.getFooter();
      ImageInfo var8 = var0.getImage();
      List var9 = var0.getFields();
      int var10 = var0.getColorRaw();
      OffsetDateTime var11 = var0.getTimestamp();
      if (var3 != null) {
         var1.setTitle(new EmbedTitle(var3, var2));
      }

      if (var4 != null) {
         var1.setDescription(var4);
      }

      if (var5 != null) {
         var1.setThumbnailUrl(var5.getUrl());
      }

      if (var6 != null) {
         var1.setAuthor(new EmbedAuthor(var6.getName(), var6.getIconUrl(), var6.getUrl()));
      }

      if (var7 != null) {
         var1.setFooter(new EmbedFooter(var7.getText(), var7.getIconUrl()));
      }

      if (var8 != null) {
         var1.setImageUrl(var8.getUrl());
      }

      if (!var9.isEmpty()) {
         var9.forEach((var1x) -> {
            var1.addField(new EmbedField(var1x.isInline(), var1x.getName(), var1x.getValue()));
         });
      }

      if (var10 != 536870911) {
         var1.setColor(var10);
      }

      if (var11 != null) {
         var1.setTimestamp(var11);
      }

      return var1;
   }

   @NotNull
   public WebhookEmbed build() {
      if (this.isEmpty()) {
         throw new IllegalStateException("Cannot build an empty embed");
      } else {
         return new WebhookEmbed(this.timestamp, this.color, this.description, this.thumbnailUrl, this.imageUrl, this.footer, this.title, this.author, new ArrayList(this.fields));
      }
   }

   private boolean isAuthorEmpty() {
      return this.author == null || this.isEmpty(this.author.getName());
   }

   @NotNull
   public WebhookEmbedBuilder setAuthor(@Nullable EmbedAuthor var1) {
      this.author = var1;
      return this;
   }

   @NotNull
   public WebhookEmbedBuilder addField(@NotNull EmbedField var1) {
      if (this.fields.size() == 25) {
         throw new IllegalStateException("Cannot add more than 25 fields");
      } else {
         this.fields.add(Objects.requireNonNull(var1));
         return this;
      }
   }

   private boolean isFieldsEmpty() {
      return this.fields.isEmpty() ? true : this.fields.stream().allMatch((var1) -> {
         return this.isEmpty(var1.getName()) && this.isEmpty(var1.getValue());
      });
   }

   @NotNull
   public WebhookEmbedBuilder setTimestamp(@Nullable TemporalAccessor var1) {
      if (var1 instanceof Instant) {
         this.timestamp = OffsetDateTime.ofInstant((Instant)var1, ZoneId.of("UTC"));
      } else {
         this.timestamp = var1 == null ? null : OffsetDateTime.from(var1);
      }

      return this;
   }

   private boolean isTitleEmpty() {
      return this.title == null || this.isEmpty(this.title.getText());
   }

   @NotNull
   public static WebhookEmbedBuilder fromD4J(@NotNull EmbedData var0) {
      WebhookEmbedBuilder var1 = new WebhookEmbedBuilder();
      Possible var2 = var0.title();
      Possible var3 = var0.description();
      Possible var4 = var0.url();
      Possible var5 = var0.timestamp();
      Possible var6 = var0.color();
      Possible var7 = var0.footer();
      Possible var8 = var0.image();
      Possible var9 = var0.thumbnail();
      Possible var10 = var0.author();
      Possible var11 = var0.fields();
      if (!var2.isAbsent()) {
         var1.setTitle(new EmbedTitle((String)var2.get(), (String)var4.toOptional().orElse((Object)null)));
      }

      if (!var3.isAbsent()) {
         var1.setDescription((String)var3.get());
      }

      if (!var5.isAbsent()) {
         var1.setTimestamp(OffsetDateTime.parse((CharSequence)var5.get()));
      }

      if (!var6.isAbsent()) {
         var1.setColor((Integer)var6.get());
      }

      if (!var7.isAbsent()) {
         var1.setFooter(new EmbedFooter(((EmbedFooterData)var7.get()).text(), (String)((EmbedFooterData)var7.get()).iconUrl().toOptional().orElse((Object)null)));
      }

      if (!var8.isAbsent()) {
         var1.setImageUrl((String)((EmbedImageData)var8.get()).url().get());
      }

      if (!var9.isAbsent()) {
         var1.setThumbnailUrl((String)((EmbedThumbnailData)var9.get()).url().get());
      }

      if (!var10.isAbsent()) {
         EmbedAuthorData var12 = (EmbedAuthorData)var10.get();
         var1.setAuthor(new EmbedAuthor((String)var12.name().get(), (String)var12.iconUrl().toOptional().orElse((Object)null), (String)var12.url().toOptional().orElse((Object)null)));
      }

      if (!var11.isAbsent()) {
         ((List)var11.get()).stream().map((var0x) -> {
            return new EmbedField((Boolean)var0x.inline().toOptional().orElse(false), var0x.name(), var0x.value());
         }).forEach(var1::addField);
      }

      return var1;
   }

   @NotNull
   public WebhookEmbedBuilder setImageUrl(@Nullable String var1) {
      this.imageUrl = var1;
      return this;
   }

   @NotNull
   public WebhookEmbedBuilder setFooter(@Nullable EmbedFooter var1) {
      this.footer = var1;
      return this;
   }

   @NotNull
   public WebhookEmbedBuilder setColor(@Nullable Integer var1) {
      this.color = var1;
      return this;
   }

   private boolean isFooterEmpty() {
      return this.footer == null || this.isEmpty(this.footer.getText());
   }

   @NotNull
   public static WebhookEmbedBuilder fromD4J(@NotNull Consumer<? super EmbedCreateSpec> var0) {
      EmbedCreateSpec var1 = new EmbedCreateSpec();
      var0.accept(var1);
      EmbedData var2 = var1.asRequest();
      return fromD4J(var2);
   }
}
