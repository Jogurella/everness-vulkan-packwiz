#version 330

// =============================================================================
//  EXAMPLE UPSCALE PACK  --  "Sharp Bilinear"
// =============================================================================
// This is the reference for making your own Purescale upscaler. Drop a folder
// (or a .zip / .rar) into the game's  upscalepacks/  directory - right next to
// resourcepacks/ and shaderpacks/ - containing:
//
//   upscalepacks/MyUpscaler/
//       upscaler.json     <- the manifest (name, shader, filter, use_sharpness)
//       shader.fsh        <- this file: one fullscreen fragment shader
//
// No Java, no rebuilding the mod. Your upscaler then shows up in the Purescale
// upscaler selector (the F9 screen) after the built-in modes.
//
// The shader contract (identical to the built-in Bilinear/Bicubic modes):
//   * vertex stage is minecraft:core/screenquad (supplied) -> gives texCoord
//   * uniform sampler2D InSampler;                 the low-res scene colour
//   * layout(std140) uniform FsrConfig { ... };    sizes + the Sharpness slider
//   * in  vec2 texCoord;                           0..1 across the output
//   * out vec4 fragColor;                          your final pixel
//   * MUST be self-contained GLSL - no #moj_import (those aren't resolved here).
//
// "filter":"linear" in the manifest binds InSampler with a LINEAR sampler, so a
// plain texture() call is already a hardware bilinear upscale. On top of that we
// add a cheap unsharp mask driven by the user's Sharpness slider (the manifest's
// "use_sharpness": true makes that value arrive in FsrConfig.Sharpness).
// -----------------------------------------------------------------------------

uniform sampler2D InSampler;

layout(std140) uniform FsrConfig {
    vec4 SizeInOut;   // xy = low-res input size (px), zw = native output size (px)
    float Sharpness;  // the user's sharpness slider, 0..1 (present because use_sharpness=true)
};

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 lowSize = SizeInOut.xy;
    vec2 texel = 1.0 / lowSize;          // one low-res texel, in UV units

    // Hardware bilinear sample (InSampler is LINEAR-filtered).
    vec3 center = texture(InSampler, texCoord).rgb;

    // 4-tap cross blur one low-res texel out, used as the unsharp low-pass.
    vec3 blur = (
        texture(InSampler, texCoord + vec2(texel.x, 0.0)).rgb +
        texture(InSampler, texCoord - vec2(texel.x, 0.0)).rgb +
        texture(InSampler, texCoord + vec2(0.0, texel.y)).rgb +
        texture(InSampler, texCoord - vec2(0.0, texel.y)).rgb) * 0.25;

    // Unsharp mask: add back the high-frequency detail (center - blur), scaled by
    // the slider. x2 so the slider's upper half gives a strong, obvious sharpen.
    vec3 sharpened = center + (center - blur) * (Sharpness * 2.0);

    fragColor = vec4(max(sharpened, vec3(0.0)), 1.0);
}
