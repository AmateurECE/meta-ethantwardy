# Autogenerated with 'bitbake -c update_crates spam-statistics'

# from Cargo.lock
SRC_URI += " \
    crate://crates.io/addr2line/0.24.2 \
    crate://crates.io/adler2/2.0.0 \
    crate://crates.io/ahash/0.8.12 \
    crate://crates.io/allocator-api2/0.2.21 \
    crate://crates.io/android-tzdata/0.1.1 \
    crate://crates.io/android_system_properties/0.1.5 \
    crate://crates.io/anstyle/1.0.10 \
    crate://crates.io/anyhow/1.0.98 \
    crate://crates.io/autocfg/1.4.0 \
    crate://crates.io/backtrace/0.3.75 \
    crate://crates.io/base64/0.12.3 \
    crate://crates.io/base64/0.22.1 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/bitflags/2.9.1 \
    crate://crates.io/bumpalo/3.17.0 \
    crate://crates.io/bytemuck/1.23.0 \
    crate://crates.io/byteorder/1.5.0 \
    crate://crates.io/cc/1.2.22 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/chrono/0.4.41 \
    crate://crates.io/chumsky/0.9.3 \
    crate://crates.io/clap/4.5.38 \
    crate://crates.io/clap_builder/4.5.38 \
    crate://crates.io/clap_derive/4.5.32 \
    crate://crates.io/clap_lex/0.7.4 \
    crate://crates.io/color_quant/1.1.0 \
    crate://crates.io/core-foundation/0.9.4 \
    crate://crates.io/core-foundation-sys/0.8.7 \
    crate://crates.io/core-graphics/0.23.2 \
    crate://crates.io/core-graphics-types/0.1.3 \
    crate://crates.io/core-text/20.1.0 \
    crate://crates.io/crc32fast/1.4.2 \
    crate://crates.io/dirs/6.0.0 \
    crate://crates.io/dirs-sys/0.5.0 \
    crate://crates.io/displaydoc/0.2.5 \
    crate://crates.io/dlib/0.5.2 \
    crate://crates.io/dwrote/0.11.3 \
    git://github.com/niax/rust-email.git;protocol=https;branch=master;name=email;destsuffix=rust-email \
    crate://crates.io/email-encoding/0.4.1 \
    crate://crates.io/email_address/0.2.9 \
    crate://crates.io/encoding_rs/0.8.35 \
    crate://crates.io/fastrand/2.3.0 \
    crate://crates.io/fdeflate/0.3.7 \
    crate://crates.io/flate2/1.1.1 \
    crate://crates.io/float-ord/0.3.2 \
    crate://crates.io/font-kit/0.14.3 \
    crate://crates.io/foreign-types/0.5.0 \
    crate://crates.io/foreign-types-macros/0.2.3 \
    crate://crates.io/foreign-types-shared/0.3.1 \
    crate://crates.io/form_urlencoded/1.2.1 \
    crate://crates.io/freetype-sys/0.20.1 \
    crate://crates.io/getrandom/0.1.16 \
    crate://crates.io/getrandom/0.2.16 \
    crate://crates.io/gif/0.12.0 \
    crate://crates.io/gimli/0.31.1 \
    crate://crates.io/hashbrown/0.14.5 \
    crate://crates.io/heck/0.5.0 \
    crate://crates.io/httpdate/1.0.3 \
    crate://crates.io/iana-time-zone/0.1.63 \
    crate://crates.io/iana-time-zone-haiku/0.1.2 \
    crate://crates.io/icu_collections/2.0.0 \
    crate://crates.io/icu_locale_core/2.0.0 \
    crate://crates.io/icu_normalizer/2.0.0 \
    crate://crates.io/icu_normalizer_data/2.0.0 \
    crate://crates.io/icu_properties/2.0.0 \
    crate://crates.io/icu_properties_data/2.0.0 \
    crate://crates.io/icu_provider/2.0.0 \
    crate://crates.io/idna/1.0.3 \
    crate://crates.io/idna_adapter/1.2.1 \
    crate://crates.io/image/0.24.9 \
    crate://crates.io/jpeg-decoder/0.3.1 \
    crate://crates.io/js-sys/0.3.77 \
    crate://crates.io/lazy_static/1.5.0 \
    crate://crates.io/lettre/0.11.16 \
    crate://crates.io/libc/0.2.172 \
    crate://crates.io/libloading/0.8.8 \
    crate://crates.io/libredox/0.1.3 \
    crate://crates.io/litemap/0.8.0 \
    crate://crates.io/log/0.4.27 \
    crate://crates.io/memchr/2.7.4 \
    crate://crates.io/mime/0.3.17 \
    crate://crates.io/miniz_oxide/0.8.8 \
    crate://crates.io/mio/1.0.3 \
    crate://crates.io/nom/8.0.0 \
    crate://crates.io/num-traits/0.2.19 \
    crate://crates.io/object/0.36.7 \
    crate://crates.io/once_cell/1.21.3 \
    crate://crates.io/option-ext/0.2.0 \
    crate://crates.io/pathfinder_geometry/0.5.1 \
    crate://crates.io/pathfinder_simd/0.5.5 \
    crate://crates.io/percent-encoding/2.3.1 \
    crate://crates.io/pin-project-lite/0.2.16 \
    crate://crates.io/pkg-config/0.3.32 \
    crate://crates.io/plotters/0.3.7 \
    crate://crates.io/plotters-backend/0.3.7 \
    crate://crates.io/plotters-bitmap/0.3.7 \
    crate://crates.io/plotters-svg/0.3.7 \
    crate://crates.io/png/0.17.16 \
    crate://crates.io/potential_utf/0.1.2 \
    crate://crates.io/ppv-lite86/0.2.21 \
    crate://crates.io/proc-macro2/1.0.95 \
    crate://crates.io/psm/0.1.26 \
    crate://crates.io/quote/1.0.40 \
    crate://crates.io/quoted_printable/0.5.1 \
    crate://crates.io/rand/0.7.3 \
    crate://crates.io/rand_chacha/0.2.2 \
    crate://crates.io/rand_core/0.5.1 \
    crate://crates.io/rand_hc/0.2.0 \
    crate://crates.io/redox_users/0.5.0 \
    crate://crates.io/regex/1.11.1 \
    crate://crates.io/regex-automata/0.4.9 \
    crate://crates.io/regex-syntax/0.8.5 \
    crate://crates.io/rustc-demangle/0.1.24 \
    crate://crates.io/rustc_version/0.4.1 \
    crate://crates.io/rustversion/1.0.20 \
    crate://crates.io/same-file/1.0.6 \
    crate://crates.io/semver/1.0.26 \
    crate://crates.io/serde/1.0.219 \
    crate://crates.io/serde_derive/1.0.219 \
    crate://crates.io/shlex/1.3.0 \
    crate://crates.io/simd-adler32/0.3.7 \
    crate://crates.io/smallvec/1.15.0 \
    crate://crates.io/socket2/0.5.9 \
    crate://crates.io/stable_deref_trait/1.2.0 \
    crate://crates.io/stacker/0.1.21 \
    crate://crates.io/syn/2.0.101 \
    crate://crates.io/synstructure/0.13.2 \
    crate://crates.io/thiserror/2.0.12 \
    crate://crates.io/thiserror-impl/2.0.12 \
    crate://crates.io/tinystr/0.8.1 \
    crate://crates.io/tokio/1.45.0 \
    crate://crates.io/ttf-parser/0.20.0 \
    crate://crates.io/unicode-ident/1.0.18 \
    crate://crates.io/url/2.5.4 \
    crate://crates.io/utf8_iter/1.0.4 \
    crate://crates.io/version_check/0.9.5 \
    crate://crates.io/walkdir/2.5.0 \
    crate://crates.io/wasi/0.9.0+wasi-snapshot-preview1 \
    crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
    crate://crates.io/wasm-bindgen/0.2.100 \
    crate://crates.io/wasm-bindgen-backend/0.2.100 \
    crate://crates.io/wasm-bindgen-macro/0.2.100 \
    crate://crates.io/wasm-bindgen-macro-support/0.2.100 \
    crate://crates.io/wasm-bindgen-shared/0.2.100 \
    crate://crates.io/web-sys/0.3.77 \
    crate://crates.io/weezl/0.1.10 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.9 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/windows-core/0.61.1 \
    crate://crates.io/windows-implement/0.60.0 \
    crate://crates.io/windows-interface/0.59.1 \
    crate://crates.io/windows-link/0.1.1 \
    crate://crates.io/windows-result/0.3.3 \
    crate://crates.io/windows-strings/0.4.1 \
    crate://crates.io/windows-sys/0.52.0 \
    crate://crates.io/windows-sys/0.59.0 \
    crate://crates.io/windows-targets/0.52.6 \
    crate://crates.io/windows_aarch64_gnullvm/0.52.6 \
    crate://crates.io/windows_aarch64_msvc/0.52.6 \
    crate://crates.io/windows_i686_gnu/0.52.6 \
    crate://crates.io/windows_i686_gnullvm/0.52.6 \
    crate://crates.io/windows_i686_msvc/0.52.6 \
    crate://crates.io/windows_x86_64_gnu/0.52.6 \
    crate://crates.io/windows_x86_64_gnullvm/0.52.6 \
    crate://crates.io/windows_x86_64_msvc/0.52.6 \
    crate://crates.io/wio/0.2.2 \
    crate://crates.io/writeable/0.6.1 \
    crate://crates.io/yeslogic-fontconfig-sys/6.0.0 \
    crate://crates.io/yoke/0.8.0 \
    crate://crates.io/yoke-derive/0.8.0 \
    crate://crates.io/zerocopy/0.8.25 \
    crate://crates.io/zerocopy-derive/0.8.25 \
    crate://crates.io/zerofrom/0.1.6 \
    crate://crates.io/zerofrom-derive/0.1.6 \
    crate://crates.io/zerotrie/0.2.2 \
    crate://crates.io/zerovec/0.11.2 \
    crate://crates.io/zerovec-derive/0.11.1 \
"

SRCREV_email = "d2b2697ce28e0cd58b6d403d4e1150a6cbdbd251"

SRC_URI[addr2line-0.24.2.sha256sum] = "dfbe277e56a376000877090da837660b4427aad530e3028d44e0bffe4f89a1c1"
SRC_URI[adler2-2.0.0.sha256sum] = "512761e0bb2578dd7380c6baaa0f4ce03e84f95e960231d1dec8bf4d7d6e2627"
SRC_URI[ahash-0.8.12.sha256sum] = "5a15f179cd60c4584b8a8c596927aadc462e27f2ca70c04e0071964a73ba7a75"
SRC_URI[allocator-api2-0.2.21.sha256sum] = "683d7910e743518b0e34f1186f92494becacb047c7b6bf616c96772180fef923"
SRC_URI[android-tzdata-0.1.1.sha256sum] = "e999941b234f3131b00bc13c22d06e8c5ff726d1b6318ac7eb276997bbb4fef0"
SRC_URI[android_system_properties-0.1.5.sha256sum] = "819e7219dbd41043ac279b19830f2efc897156490d7fd6ea916720117ee66311"
SRC_URI[anstyle-1.0.10.sha256sum] = "55cc3b69f167a1ef2e161439aa98aed94e6028e5f9a59be9a6ffb47aef1651f9"
SRC_URI[anyhow-1.0.98.sha256sum] = "e16d2d3311acee920a9eb8d33b8cbc1787ce4a264e85f964c2404b969bdcd487"
SRC_URI[autocfg-1.4.0.sha256sum] = "ace50bade8e6234aa140d9a2f552bbee1db4d353f69b8217bc503490fc1a9f26"
SRC_URI[backtrace-0.3.75.sha256sum] = "6806a6321ec58106fea15becdad98371e28d92ccbc7c8f1b3b6dd724fe8f1002"
SRC_URI[base64-0.12.3.sha256sum] = "3441f0f7b02788e948e47f457ca01f1d7e6d92c693bc132c22b087d3141c03ff"
SRC_URI[base64-0.22.1.sha256sum] = "72b3254f16251a8381aa12e40e3c4d2f0199f8c6508fbecb9d91f575e0fbb8c6"
SRC_URI[bitflags-1.3.2.sha256sum] = "bef38d45163c2f1dde094a7dfd33ccf595c92905c8f8f4fdc18d06fb1037718a"
SRC_URI[bitflags-2.9.1.sha256sum] = "1b8e56985ec62d17e9c1001dc89c88ecd7dc08e47eba5ec7c29c7b5eeecde967"
SRC_URI[bumpalo-3.17.0.sha256sum] = "1628fb46dfa0b37568d12e5edd512553eccf6a22a78e8bde00bb4aed84d5bdbf"
SRC_URI[bytemuck-1.23.0.sha256sum] = "9134a6ef01ce4b366b50689c94f82c14bc72bc5d0386829828a2e2752ef7958c"
SRC_URI[byteorder-1.5.0.sha256sum] = "1fd0f2584146f6f2ef48085050886acf353beff7305ebd1ae69500e27c67f64b"
SRC_URI[cc-1.2.22.sha256sum] = "32db95edf998450acc7881c932f94cd9b05c87b4b2599e8bab064753da4acfd1"
SRC_URI[cfg-if-1.0.0.sha256sum] = "baf1de4339761588bc0619e3cbc0120ee582ebb74b53b4efbf79117bd2da40fd"
SRC_URI[chrono-0.4.41.sha256sum] = "c469d952047f47f91b68d1cba3f10d63c11d73e4636f24f08daf0278abf01c4d"
SRC_URI[chumsky-0.9.3.sha256sum] = "8eebd66744a15ded14960ab4ccdbfb51ad3b81f51f3f04a80adac98c985396c9"
SRC_URI[clap-4.5.38.sha256sum] = "ed93b9805f8ba930df42c2590f05453d5ec36cbb85d018868a5b24d31f6ac000"
SRC_URI[clap_builder-4.5.38.sha256sum] = "379026ff283facf611b0ea629334361c4211d1b12ee01024eec1591133b04120"
SRC_URI[clap_derive-4.5.32.sha256sum] = "09176aae279615badda0765c0c0b3f6ed53f4709118af73cf4655d85d1530cd7"
SRC_URI[clap_lex-0.7.4.sha256sum] = "f46ad14479a25103f283c0f10005961cf086d8dc42205bb44c46ac563475dca6"
SRC_URI[color_quant-1.1.0.sha256sum] = "3d7b894f5411737b7867f4827955924d7c254fc9f4d91a6aad6b097804b1018b"
SRC_URI[core-foundation-0.9.4.sha256sum] = "91e195e091a93c46f7102ec7818a2aa394e1e1771c3ab4825963fa03e45afb8f"
SRC_URI[core-foundation-sys-0.8.7.sha256sum] = "773648b94d0e5d620f64f280777445740e61fe701025087ec8b57f45c791888b"
SRC_URI[core-graphics-0.23.2.sha256sum] = "c07782be35f9e1140080c6b96f0d44b739e2278479f64e02fdab4e32dfd8b081"
SRC_URI[core-graphics-types-0.1.3.sha256sum] = "45390e6114f68f718cc7a830514a96f903cccd70d02a8f6d9f643ac4ba45afaf"
SRC_URI[core-text-20.1.0.sha256sum] = "c9d2790b5c08465d49f8dc05c8bcae9fea467855947db39b0f8145c091aaced5"
SRC_URI[crc32fast-1.4.2.sha256sum] = "a97769d94ddab943e4510d138150169a2758b5ef3eb191a9ee688de3e23ef7b3"
SRC_URI[dirs-6.0.0.sha256sum] = "c3e8aa94d75141228480295a7d0e7feb620b1a5ad9f12bc40be62411e38cce4e"
SRC_URI[dirs-sys-0.5.0.sha256sum] = "e01a3366d27ee9890022452ee61b2b63a67e6f13f58900b651ff5665f0bb1fab"
SRC_URI[displaydoc-0.2.5.sha256sum] = "97369cbbc041bc366949bc74d34658d6cda5621039731c6310521892a3a20ae0"
SRC_URI[dlib-0.5.2.sha256sum] = "330c60081dcc4c72131f8eb70510f1ac07223e5d4163db481a04a0befcffa412"
SRC_URI[dwrote-0.11.3.sha256sum] = "bfe1f192fcce01590bd8d839aca53ce0d11d803bf291b2a6c4ad925a8f0024be"
SRC_URI[email-encoding-0.4.1.sha256sum] = "9298e6504d9b9e780ed3f7dfd43a61be8cd0e09eb07f7706a945b0072b6670b6"
SRC_URI[email_address-0.2.9.sha256sum] = "e079f19b08ca6239f47f8ba8509c11cf3ea30095831f7fed61441475edd8c449"
SRC_URI[encoding_rs-0.8.35.sha256sum] = "75030f3c4f45dafd7586dd6780965a8c7e8e285a5ecb86713e63a79c5b2766f3"
SRC_URI[fastrand-2.3.0.sha256sum] = "37909eebbb50d72f9059c3b6d82c0463f2ff062c9e95845c43a6c9c0355411be"
SRC_URI[fdeflate-0.3.7.sha256sum] = "1e6853b52649d4ac5c0bd02320cddc5ba956bdb407c4b75a2c6b75bf51500f8c"
SRC_URI[flate2-1.1.1.sha256sum] = "7ced92e76e966ca2fd84c8f7aa01a4aea65b0eb6648d72f7c8f3e2764a67fece"
SRC_URI[float-ord-0.3.2.sha256sum] = "8ce81f49ae8a0482e4c55ea62ebbd7e5a686af544c00b9d090bba3ff9be97b3d"
SRC_URI[font-kit-0.14.3.sha256sum] = "2c7e611d49285d4c4b2e1727b72cf05353558885cc5252f93707b845dfcaf3d3"
SRC_URI[foreign-types-0.5.0.sha256sum] = "d737d9aa519fb7b749cbc3b962edcf310a8dd1f4b67c91c4f83975dbdd17d965"
SRC_URI[foreign-types-macros-0.2.3.sha256sum] = "1a5c6c585bc94aaf2c7b51dd4c2ba22680844aba4c687be581871a6f518c5742"
SRC_URI[foreign-types-shared-0.3.1.sha256sum] = "aa9a19cbb55df58761df49b23516a86d432839add4af60fc256da840f66ed35b"
SRC_URI[form_urlencoded-1.2.1.sha256sum] = "e13624c2627564efccf4934284bdd98cbaa14e79b0b5a141218e507b3a823456"
SRC_URI[freetype-sys-0.20.1.sha256sum] = "0e7edc5b9669349acfda99533e9e0bcf26a51862ab43b08ee7745c55d28eb134"
SRC_URI[getrandom-0.1.16.sha256sum] = "8fc3cb4d91f53b50155bdcfd23f6a4c39ae1969c2ae85982b135750cccaf5fce"
SRC_URI[getrandom-0.2.16.sha256sum] = "335ff9f135e4384c8150d6f27c6daed433577f86b4750418338c01a1a2528592"
SRC_URI[gif-0.12.0.sha256sum] = "80792593675e051cf94a4b111980da2ba60d4a83e43e0048c5693baab3977045"
SRC_URI[gimli-0.31.1.sha256sum] = "07e28edb80900c19c28f1072f2e8aeca7fa06b23cd4169cefe1af5aa3260783f"
SRC_URI[hashbrown-0.14.5.sha256sum] = "e5274423e17b7c9fc20b6e7e208532f9b19825d82dfd615708b70edd83df41f1"
SRC_URI[heck-0.5.0.sha256sum] = "2304e00983f87ffb38b55b444b5e3b60a884b5d30c0fca7d82fe33449bbe55ea"
SRC_URI[httpdate-1.0.3.sha256sum] = "df3b46402a9d5adb4c86a0cf463f42e19994e3ee891101b1841f30a545cb49a9"
SRC_URI[iana-time-zone-0.1.63.sha256sum] = "b0c919e5debc312ad217002b8048a17b7d83f80703865bbfcfebb0458b0b27d8"
SRC_URI[iana-time-zone-haiku-0.1.2.sha256sum] = "f31827a206f56af32e590ba56d5d2d085f558508192593743f16b2306495269f"
SRC_URI[icu_collections-2.0.0.sha256sum] = "200072f5d0e3614556f94a9930d5dc3e0662a652823904c3a75dc3b0af7fee47"
SRC_URI[icu_locale_core-2.0.0.sha256sum] = "0cde2700ccaed3872079a65fb1a78f6c0a36c91570f28755dda67bc8f7d9f00a"
SRC_URI[icu_normalizer-2.0.0.sha256sum] = "436880e8e18df4d7bbc06d58432329d6458cc84531f7ac5f024e93deadb37979"
SRC_URI[icu_normalizer_data-2.0.0.sha256sum] = "00210d6893afc98edb752b664b8890f0ef174c8adbb8d0be9710fa66fbbf72d3"
SRC_URI[icu_properties-2.0.0.sha256sum] = "2549ca8c7241c82f59c80ba2a6f415d931c5b58d24fb8412caa1a1f02c49139a"
SRC_URI[icu_properties_data-2.0.0.sha256sum] = "8197e866e47b68f8f7d95249e172903bec06004b18b2937f1095d40a0c57de04"
SRC_URI[icu_provider-2.0.0.sha256sum] = "03c80da27b5f4187909049ee2d72f276f0d9f99a42c306bd0131ecfe04d8e5af"
SRC_URI[idna-1.0.3.sha256sum] = "686f825264d630750a544639377bae737628043f20d38bbc029e8f29ea968a7e"
SRC_URI[idna_adapter-1.2.1.sha256sum] = "3acae9609540aa318d1bc588455225fb2085b9ed0c4f6bd0d9d5bcd86f1a0344"
SRC_URI[image-0.24.9.sha256sum] = "5690139d2f55868e080017335e4b94cb7414274c74f1669c84fb5feba2c9f69d"
SRC_URI[jpeg-decoder-0.3.1.sha256sum] = "f5d4a7da358eff58addd2877a45865158f0d78c911d43a5784ceb7bbf52833b0"
SRC_URI[js-sys-0.3.77.sha256sum] = "1cfaf33c695fc6e08064efbc1f72ec937429614f25eef83af942d0e227c3a28f"
SRC_URI[lazy_static-1.5.0.sha256sum] = "bbd2bcb4c963f2ddae06a2efc7e9f3591312473c50c6685e1f298068316e66fe"
SRC_URI[lettre-0.11.16.sha256sum] = "87ffd14fa289730e3ad68edefdc31f603d56fe716ec38f2076bb7410e09147c2"
SRC_URI[libc-0.2.172.sha256sum] = "d750af042f7ef4f724306de029d18836c26c1765a54a6a3f094cbd23a7267ffa"
SRC_URI[libloading-0.8.8.sha256sum] = "07033963ba89ebaf1584d767badaa2e8fcec21aedea6b8c0346d487d49c28667"
SRC_URI[libredox-0.1.3.sha256sum] = "c0ff37bd590ca25063e35af745c343cb7a0271906fb7b37e4813e8f79f00268d"
SRC_URI[litemap-0.8.0.sha256sum] = "241eaef5fd12c88705a01fc1066c48c4b36e0dd4377dcdc7ec3942cea7a69956"
SRC_URI[log-0.4.27.sha256sum] = "13dc2df351e3202783a1fe0d44375f7295ffb4049267b0f3018346dc122a1d94"
SRC_URI[memchr-2.7.4.sha256sum] = "78ca9ab1a0babb1e7d5695e3530886289c18cf2f87ec19a575a0abdce112e3a3"
SRC_URI[mime-0.3.17.sha256sum] = "6877bb514081ee2a7ff5ef9de3281f14a4dd4bceac4c09388074a6b5df8a139a"
SRC_URI[miniz_oxide-0.8.8.sha256sum] = "3be647b768db090acb35d5ec5db2b0e1f1de11133ca123b9eacf5137868f892a"
SRC_URI[mio-1.0.3.sha256sum] = "2886843bf800fba2e3377cff24abf6379b4c4d5c6681eaf9ea5b0d15090450bd"
SRC_URI[nom-8.0.0.sha256sum] = "df9761775871bdef83bee530e60050f7e54b1105350d6884eb0fb4f46c2f9405"
SRC_URI[num-traits-0.2.19.sha256sum] = "071dfc062690e90b734c0b2273ce72ad0ffa95f0c74596bc250dcfd960262841"
SRC_URI[object-0.36.7.sha256sum] = "62948e14d923ea95ea2c7c86c71013138b66525b86bdc08d2dcc262bdb497b87"
SRC_URI[once_cell-1.21.3.sha256sum] = "42f5e15c9953c5e4ccceeb2e7382a716482c34515315f7b03532b8b4e8393d2d"
SRC_URI[option-ext-0.2.0.sha256sum] = "04744f49eae99ab78e0d5c0b603ab218f515ea8cfe5a456d7629ad883a3b6e7d"
SRC_URI[pathfinder_geometry-0.5.1.sha256sum] = "0b7b7e7b4ea703700ce73ebf128e1450eb69c3a8329199ffbfb9b2a0418e5ad3"
SRC_URI[pathfinder_simd-0.5.5.sha256sum] = "bf9027960355bf3afff9841918474a81a5f972ac6d226d518060bba758b5ad57"
SRC_URI[percent-encoding-2.3.1.sha256sum] = "e3148f5046208a5d56bcfc03053e3ca6334e51da8dfb19b6cdc8b306fae3283e"
SRC_URI[pin-project-lite-0.2.16.sha256sum] = "3b3cff922bd51709b605d9ead9aa71031d81447142d828eb4a6eba76fe619f9b"
SRC_URI[pkg-config-0.3.32.sha256sum] = "7edddbd0b52d732b21ad9a5fab5c704c14cd949e5e9a1ec5929a24fded1b904c"
SRC_URI[plotters-0.3.7.sha256sum] = "5aeb6f403d7a4911efb1e33402027fc44f29b5bf6def3effcc22d7bb75f2b747"
SRC_URI[plotters-backend-0.3.7.sha256sum] = "df42e13c12958a16b3f7f4386b9ab1f3e7933914ecea48da7139435263a4172a"
SRC_URI[plotters-bitmap-0.3.7.sha256sum] = "72ce181e3f6bf82d6c1dc569103ca7b1bd964c60ba03d7e6cdfbb3e3eb7f7405"
SRC_URI[plotters-svg-0.3.7.sha256sum] = "51bae2ac328883f7acdfea3d66a7c35751187f870bc81f94563733a154d7a670"
SRC_URI[png-0.17.16.sha256sum] = "82151a2fc869e011c153adc57cf2789ccb8d9906ce52c0b39a6b5697749d7526"
SRC_URI[potential_utf-0.1.2.sha256sum] = "e5a7c30837279ca13e7c867e9e40053bc68740f988cb07f7ca6df43cc734b585"
SRC_URI[ppv-lite86-0.2.21.sha256sum] = "85eae3c4ed2f50dcfe72643da4befc30deadb458a9b590d720cde2f2b1e97da9"
SRC_URI[proc-macro2-1.0.95.sha256sum] = "02b3e5e68a3a1a02aad3ec490a98007cbc13c37cbe84a3cd7b8e406d76e7f778"
SRC_URI[psm-0.1.26.sha256sum] = "6e944464ec8536cd1beb0bbfd96987eb5e3b72f2ecdafdc5c769a37f1fa2ae1f"
SRC_URI[quote-1.0.40.sha256sum] = "1885c039570dc00dcb4ff087a89e185fd56bae234ddc7f056a945bf36467248d"
SRC_URI[quoted_printable-0.5.1.sha256sum] = "640c9bd8497b02465aeef5375144c26062e0dcd5939dfcbb0f5db76cb8c17c73"
SRC_URI[rand-0.7.3.sha256sum] = "6a6b1679d49b24bbfe0c803429aa1874472f50d9b363131f0e89fc356b544d03"
SRC_URI[rand_chacha-0.2.2.sha256sum] = "f4c8ed856279c9737206bf725bf36935d8666ead7aa69b52be55af369d193402"
SRC_URI[rand_core-0.5.1.sha256sum] = "90bde5296fc891b0cef12a6d03ddccc162ce7b2aff54160af9338f8d40df6d19"
SRC_URI[rand_hc-0.2.0.sha256sum] = "ca3129af7b92a17112d59ad498c6f81eaf463253766b90396d39ea7a39d6613c"
SRC_URI[redox_users-0.5.0.sha256sum] = "dd6f9d3d47bdd2ad6945c5015a226ec6155d0bcdfd8f7cd29f86b71f8de99d2b"
SRC_URI[regex-1.11.1.sha256sum] = "b544ef1b4eac5dc2db33ea63606ae9ffcfac26c1416a2806ae0bf5f56b201191"
SRC_URI[regex-automata-0.4.9.sha256sum] = "809e8dc61f6de73b46c85f4c96486310fe304c434cfa43669d7b40f711150908"
SRC_URI[regex-syntax-0.8.5.sha256sum] = "2b15c43186be67a4fd63bee50d0303afffcef381492ebe2c5d87f324e1b8815c"
SRC_URI[rustc-demangle-0.1.24.sha256sum] = "719b953e2095829ee67db738b3bfa9fa368c94900df327b3f07fe6e794d2fe1f"
SRC_URI[rustc_version-0.4.1.sha256sum] = "cfcb3a22ef46e85b45de6ee7e79d063319ebb6594faafcf1c225ea92ab6e9b92"
SRC_URI[rustversion-1.0.20.sha256sum] = "eded382c5f5f786b989652c49544c4877d9f015cc22e145a5ea8ea66c2921cd2"
SRC_URI[same-file-1.0.6.sha256sum] = "93fc1dc3aaa9bfed95e02e6eadabb4baf7e3078b0bd1b4d7b6b0b68378900502"
SRC_URI[semver-1.0.26.sha256sum] = "56e6fa9c48d24d85fb3de5ad847117517440f6beceb7798af16b4a87d616b8d0"
SRC_URI[serde-1.0.219.sha256sum] = "5f0e2c6ed6606019b4e29e69dbaba95b11854410e5347d525002456dbbb786b6"
SRC_URI[serde_derive-1.0.219.sha256sum] = "5b0276cf7f2c73365f7157c8123c21cd9a50fbbd844757af28ca1f5925fc2a00"
SRC_URI[shlex-1.3.0.sha256sum] = "0fda2ff0d084019ba4d7c6f371c95d8fd75ce3524c3cb8fb653a3023f6323e64"
SRC_URI[simd-adler32-0.3.7.sha256sum] = "d66dc143e6b11c1eddc06d5c423cfc97062865baf299914ab64caa38182078fe"
SRC_URI[smallvec-1.15.0.sha256sum] = "8917285742e9f3e1683f0a9c4e6b57960b7314d0b08d30d1ecd426713ee2eee9"
SRC_URI[socket2-0.5.9.sha256sum] = "4f5fd57c80058a56cf5c777ab8a126398ece8e442983605d280a44ce79d0edef"
SRC_URI[stable_deref_trait-1.2.0.sha256sum] = "a8f112729512f8e442d81f95a8a7ddf2b7c6b8a1a6f509a95864142b30cab2d3"
SRC_URI[stacker-0.1.21.sha256sum] = "cddb07e32ddb770749da91081d8d0ac3a16f1a569a18b20348cd371f5dead06b"
SRC_URI[syn-2.0.101.sha256sum] = "8ce2b7fc941b3a24138a0a7cf8e858bfc6a992e7978a068a5c760deb0ed43caf"
SRC_URI[synstructure-0.13.2.sha256sum] = "728a70f3dbaf5bab7f0c4b1ac8d7ae5ea60a4b5549c8a5914361c99147a709d2"
SRC_URI[thiserror-2.0.12.sha256sum] = "567b8a2dae586314f7be2a752ec7474332959c6460e02bde30d702a66d488708"
SRC_URI[thiserror-impl-2.0.12.sha256sum] = "7f7cf42b4507d8ea322120659672cf1b9dbb93f8f2d4ecfd6e51350ff5b17a1d"
SRC_URI[tinystr-0.8.1.sha256sum] = "5d4f6d1145dcb577acf783d4e601bc1d76a13337bb54e6233add580b07344c8b"
SRC_URI[tokio-1.45.0.sha256sum] = "2513ca694ef9ede0fb23fe71a4ee4107cb102b9dc1930f6d0fd77aae068ae165"
SRC_URI[ttf-parser-0.20.0.sha256sum] = "17f77d76d837a7830fe1d4f12b7b4ba4192c1888001c7164257e4bc6d21d96b4"
SRC_URI[unicode-ident-1.0.18.sha256sum] = "5a5f39404a5da50712a4c1eecf25e90dd62b613502b7e925fd4e4d19b5c96512"
SRC_URI[url-2.5.4.sha256sum] = "32f8b686cadd1473f4bd0117a5d28d36b1ade384ea9b5069a1c40aefed7fda60"
SRC_URI[utf8_iter-1.0.4.sha256sum] = "b6c140620e7ffbb22c2dee59cafe6084a59b5ffc27a8859a5f0d494b5d52b6be"
SRC_URI[version_check-0.9.5.sha256sum] = "0b928f33d975fc6ad9f86c8f283853ad26bdd5b10b7f1542aa2fa15e2289105a"
SRC_URI[walkdir-2.5.0.sha256sum] = "29790946404f91d9c5d06f9874efddea1dc06c5efe94541a7d6863108e3a5e4b"
SRC_URI[wasi-0.9.0+wasi-snapshot-preview1.sha256sum] = "cccddf32554fecc6acb585f82a32a72e28b48f8c4c1883ddfeeeaa96f7d8e519"
SRC_URI[wasi-0.11.0+wasi-snapshot-preview1.sha256sum] = "9c8d87e72b64a3b4db28d11ce29237c246188f4f51057d65a7eab63b7987e423"
SRC_URI[wasm-bindgen-0.2.100.sha256sum] = "1edc8929d7499fc4e8f0be2262a241556cfc54a0bea223790e71446f2aab1ef5"
SRC_URI[wasm-bindgen-backend-0.2.100.sha256sum] = "2f0a0651a5c2bc21487bde11ee802ccaf4c51935d0d3d42a6101f98161700bc6"
SRC_URI[wasm-bindgen-macro-0.2.100.sha256sum] = "7fe63fc6d09ed3792bd0897b314f53de8e16568c2b3f7982f468c0bf9bd0b407"
SRC_URI[wasm-bindgen-macro-support-0.2.100.sha256sum] = "8ae87ea40c9f689fc23f209965b6fb8a99ad69aeeb0231408be24920604395de"
SRC_URI[wasm-bindgen-shared-0.2.100.sha256sum] = "1a05d73b933a847d6cccdda8f838a22ff101ad9bf93e33684f39c1f5f0eece3d"
SRC_URI[web-sys-0.3.77.sha256sum] = "33b6dd2ef9186f1f2072e409e99cd22a975331a6b3591b12c764e0e55c60d5d2"
SRC_URI[weezl-0.1.10.sha256sum] = "a751b3277700db47d3e574514de2eced5e54dc8a5436a3bf7a0b248b2cee16f3"
SRC_URI[winapi-0.3.9.sha256sum] = "5c839a674fcd7a98952e593242ea400abe93992746761e38641405d28b00f419"
SRC_URI[winapi-i686-pc-windows-gnu-0.4.0.sha256sum] = "ac3b87c63620426dd9b991e5ce0329eff545bccbbb34f3be09ff6fb6ab51b7b6"
SRC_URI[winapi-util-0.1.9.sha256sum] = "cf221c93e13a30d793f7645a0e7762c55d169dbb0a49671918a2319d289b10bb"
SRC_URI[winapi-x86_64-pc-windows-gnu-0.4.0.sha256sum] = "712e227841d057c1ee1cd2fb22fa7e5a5461ae8e48fa2ca79ec42cfc1931183f"
SRC_URI[windows-core-0.61.1.sha256sum] = "46ec44dc15085cea82cf9c78f85a9114c463a369786585ad2882d1ff0b0acf40"
SRC_URI[windows-implement-0.60.0.sha256sum] = "a47fddd13af08290e67f4acabf4b459f647552718f683a7b415d290ac744a836"
SRC_URI[windows-interface-0.59.1.sha256sum] = "bd9211b69f8dcdfa817bfd14bf1c97c9188afa36f4750130fcdf3f400eca9fa8"
SRC_URI[windows-link-0.1.1.sha256sum] = "76840935b766e1b0a05c0066835fb9ec80071d4c09a16f6bd5f7e655e3c14c38"
SRC_URI[windows-result-0.3.3.sha256sum] = "4b895b5356fc36103d0f64dd1e94dfa7ac5633f1c9dd6e80fe9ec4adef69e09d"
SRC_URI[windows-strings-0.4.1.sha256sum] = "2a7ab927b2637c19b3dbe0965e75d8f2d30bdd697a1516191cad2ec4df8fb28a"
SRC_URI[windows-sys-0.52.0.sha256sum] = "282be5f36a8ce781fad8c8ae18fa3f9beff57ec1b52cb3de0789201425d9a33d"
SRC_URI[windows-sys-0.59.0.sha256sum] = "1e38bc4d79ed67fd075bcc251a1c39b32a1776bbe92e5bef1f0bf1f8c531853b"
SRC_URI[windows-targets-0.52.6.sha256sum] = "9b724f72796e036ab90c1021d4780d4d3d648aca59e491e6b98e725b84e99973"
SRC_URI[windows_aarch64_gnullvm-0.52.6.sha256sum] = "32a4622180e7a0ec044bb555404c800bc9fd9ec262ec147edd5989ccd0c02cd3"
SRC_URI[windows_aarch64_msvc-0.52.6.sha256sum] = "09ec2a7bb152e2252b53fa7803150007879548bc709c039df7627cabbd05d469"
SRC_URI[windows_i686_gnu-0.52.6.sha256sum] = "8e9b5ad5ab802e97eb8e295ac6720e509ee4c243f69d781394014ebfe8bbfa0b"
SRC_URI[windows_i686_gnullvm-0.52.6.sha256sum] = "0eee52d38c090b3caa76c563b86c3a4bd71ef1a819287c19d586d7334ae8ed66"
SRC_URI[windows_i686_msvc-0.52.6.sha256sum] = "240948bc05c5e7c6dabba28bf89d89ffce3e303022809e73deaefe4f6ec56c66"
SRC_URI[windows_x86_64_gnu-0.52.6.sha256sum] = "147a5c80aabfbf0c7d901cb5895d1de30ef2907eb21fbbab29ca94c5b08b1a78"
SRC_URI[windows_x86_64_gnullvm-0.52.6.sha256sum] = "24d5b23dc417412679681396f2b49f3de8c1473deb516bd34410872eff51ed0d"
SRC_URI[windows_x86_64_msvc-0.52.6.sha256sum] = "589f6da84c646204747d1270a2a5661ea66ed1cced2631d546fdfb155959f9ec"
SRC_URI[wio-0.2.2.sha256sum] = "5d129932f4644ac2396cb456385cbf9e63b5b30c6e8dc4820bdca4eb082037a5"
SRC_URI[writeable-0.6.1.sha256sum] = "ea2f10b9bb0928dfb1b42b65e1f9e36f7f54dbdf08457afefb38afcdec4fa2bb"
SRC_URI[yeslogic-fontconfig-sys-6.0.0.sha256sum] = "503a066b4c037c440169d995b869046827dbc71263f6e8f3be6d77d4f3229dbd"
SRC_URI[yoke-0.8.0.sha256sum] = "5f41bb01b8226ef4bfd589436a297c53d118f65921786300e427be8d487695cc"
SRC_URI[yoke-derive-0.8.0.sha256sum] = "38da3c9736e16c5d3c8c597a9aaa5d1fa565d0532ae05e27c24aa62fb32c0ab6"
SRC_URI[zerocopy-0.8.25.sha256sum] = "a1702d9583232ddb9174e01bb7c15a2ab8fb1bc6f227aa1233858c351a3ba0cb"
SRC_URI[zerocopy-derive-0.8.25.sha256sum] = "28a6e20d751156648aa063f3800b706ee209a32c0b4d9f24be3d980b01be55ef"
SRC_URI[zerofrom-0.1.6.sha256sum] = "50cc42e0333e05660c3587f3bf9d0478688e15d870fab3346451ce7f8c9fbea5"
SRC_URI[zerofrom-derive-0.1.6.sha256sum] = "d71e5d6e06ab090c67b5e44993ec16b72dcbaabc526db883a360057678b48502"
SRC_URI[zerotrie-0.2.2.sha256sum] = "36f0bbd478583f79edad978b407914f61b2972f5af6fa089686016be8f9af595"
SRC_URI[zerovec-0.11.2.sha256sum] = "4a05eb080e015ba39cc9e23bbe5e7fb04d5fb040350f99f34e338d5fdd294428"
SRC_URI[zerovec-derive-0.11.1.sha256sum] = "5b96237efa0c878c64bd89c436f661be4e46b2f3eff1ebb976f7ef2321d2f58f"
